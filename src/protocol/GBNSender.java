package protocol;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Reference: https://github.com/haochending/Go-Back-N/
 */

public class GBNSender {
    private final static int windowSize = 10;
    private final static int TIME_OUT = 3000;

    private int sequenceBase = 0;
    private int nextSequenceNumber = 0;

    private int FAILURE_RATE = 5;

    private List<GBNPacket> unAckEdPackets = new LinkedList<GBNPacket>();

    private FileOutputStream numberSequenceOutput;
    private FileOutputStream ack;

    private Random random;

    private static Object lock = new Object();

    public GBNSender(FileOutputStream numberSequenceOutput, FileOutputStream ack) {
        this.ack = ack;
        this.numberSequenceOutput = numberSequenceOutput;

        this.random = new Random();
    }

    public void send(final InetAddress address, final int port, final FileInputStream fis, final DatagramSocket socket)
        throws InterruptedException {

        Thread SenderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendPackets(fis, address, port, socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread AckThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listenAck(address, port, socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        SenderThread.start();
        AckThread.start();

        SenderThread.join();
        AckThread.join();

        socket.close();
    }

    private void sendPackets(FileInputStream fis, InetAddress target, int port, DatagramSocket socket)
        throws IOException {
        int ret;
        GBNPacket packet;
        byte[] buffer = new byte[500];

        while (true) {
            if (nextSequenceNumber >= sequenceBase + windowSize) {
                continue;
            }

            synchronized (lock) {
                ret = fis.read(buffer);

                // end-of-transmission
                if (ret < 0) {
                    packet = GBNPacket.createEndOfTransmission(nextSequenceNumber);
                    DatagramPacket senderPacket = new DatagramPacket(
                        packet.getUDPData(), packet.getUDPData().length, target, port);
                    socket.send(senderPacket);
                    System.out.println("Debug:EOT " + packet.getSequenceNumber());

                    unAckEdPackets.add(packet);

                    numberSequenceOutput.write((Integer.toString(nextSequenceNumber) + '\n').getBytes());
                    fis.close();
                    break;
                } else {
                    // normal packet
                    packet = new GBNPacket(GBNPacket.DATA_PACKET, nextSequenceNumber, new String(buffer,
                        0, ret));

                    DatagramPacket senderPacket = new DatagramPacket(
                        packet.getUDPData(), packet.getUDPData().length, target,
                        port);

                    if (random.nextInt(100) <= FAILURE_RATE) {
                        socket.send(senderPacket);
                    }

                    System.out.println("Debug: Send " + packet.getSequenceNumber());

                    unAckEdPackets.add(packet);

                    numberSequenceOutput.write((Integer.toString(nextSequenceNumber) + '\n').getBytes());

                    nextSequenceNumber++;
                }
            }
        }
    }

    private void listenAck(InetAddress target, int port, DatagramSocket socket) throws IOException {

        while (true) {

            try {
                byte[] buffer = new byte[GBNPacket.PACKET_LENGTH];

                socket.setSoTimeout(TIME_OUT);
                DatagramPacket ACKs = new DatagramPacket(buffer, buffer.length,
                    target, port);
                socket.receive(ACKs);

                GBNPacket ACKPackets = GBNPacket.parseUDPData(ACKs.getData());

                // if the packet is EOT just write to log file and exit
                if (ACKPackets.getType() == 2) {
                    ack.write((Integer.toString(ACKPackets.getSequenceNumber()) + '\n')
                        .getBytes());
                    break;
                }

                synchronized (lock) {
                    // difference between the seqNum of ACK and the base
                    int diff = ACKPackets.getSequenceNumber() - (sequenceBase % GBNPacket.SEQUENCE_MOD) + 1;
                    if (diff > 0) { // ignore duplicate ACKs

                        // write to log file
                        ack.write((Integer.toString(ACKPackets.getSequenceNumber()) + '\n')
                            .getBytes());

                        sequenceBase += diff;

                        // remove the first diff elements in the unAckedPackets
                        for (int i = 0; i < diff; i++) {
                            System.out.println("Debug:Remove " + unAckEdPackets.get(i).getSequenceNumber());

                            if (unAckEdPackets.isEmpty()) {
                                break;
                            }
                            unAckEdPackets.remove(i);
                        }
                    }
                }
            } catch (SocketTimeoutException ex) {
                synchronized (lock) {
                    for (GBNPacket unAckEdPacket : unAckEdPackets) {
                        socket.send(new DatagramPacket(unAckEdPacket.getUDPData(),
                                unAckEdPacket.getUDPData().length,
                                target,
                                port)
                        );

                        numberSequenceOutput.write((Integer.toString(unAckEdPacket.getSequenceNumber()) + '\n').getBytes());

                        System.out.println("Timeout resend " + unAckEdPacket.getSequenceNumber());
                    }
                }
            }
        }
    }
}
