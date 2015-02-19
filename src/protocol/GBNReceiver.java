package protocol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Reference https://github.com/haochending/Go-Back-N/
 */

public class GBNReceiver {
    private InetAddress hostAddress;
    private int senderPort;
    private static FileOutputStream output;
    private static DatagramSocket receiverSocket;

    private byte[] window = new byte[1024];

    private int nextSequenceNumber = 0;

    private int endOfFile = 0;

    public GBNReceiver(InetAddress address, Integer senderPort, Integer receiverPort, File writtenFile)
        throws IOException {

        hostAddress = address;
        this.senderPort = senderPort;

        receiverSocket = new DatagramSocket(receiverPort);
        output = new FileOutputStream(writtenFile);
    }

    public void start() throws IOException {
        GBNPacket packet;
        DatagramPacket receiverPacket;
        int type;

        while (endOfFile != 1) {
            receiverPacket = new DatagramPacket(window, window.length);

            try {
                receiverSocket.receive(receiverPacket);
            } catch (IOException e) {
                System.err.println("Receiver Socket I/O exception!!!");
            }

            packet = GBNPacket.parseUDPData(window);

            type = packet.getType();

            if (type == GBNPacket.DATA_PACKET) {
                if (packet.getSequenceNumber() == nextSequenceNumber) {
                    writeToFile(packet);
                    System.out.print("Debug: ack");
                    sendACK(nextSequenceNumber % GBNPacket.SEQUENCE_MOD);
                    nextSequenceNumber += 1;
                    return;
                }

                System.out.print("Debug: resend");

                if (nextSequenceNumber != 0) {
                    sendACK(nextSequenceNumber % GBNPacket.SEQUENCE_MOD - 1);
                }

                return;
            } else if (type == GBNPacket.END_OF_TRANSMISSION_PACKET) {
                if (packet.getLength() != 0) {
                    System.err.println("Invalid packet length for EOT received.");
                    return;
                }

                // if it's the expected packet means it's the end of file
                // send EOT to the sender and close the file output stream
                // update endOfFile to break the while loop
                if (packet.getSequenceNumber() == nextSequenceNumber % GBNPacket.SEQUENCE_MOD) {
                    endOfFile = 1;
                    sendEndOfTransmission(packet.getSequenceNumber());
                    return;
                }

                // if it's not just send the ACK packet with the sequenceNumber
                // of the packet before the expected one
                if (nextSequenceNumber != 0) {
                    sendACK(nextSequenceNumber % GBNPacket.SEQUENCE_MOD - 1);
                }
            } else if (type == GBNPacket.ACK_PACKET) {
                System.err.println("Invalid packet type received!!!");
                return;
            }
        }

        receiverSocket.close();
    }

    private void sendEndOfTransmission(int sequenceNumber) throws IOException {
        GBNPacket eot = GBNPacket.createEndOfTransmission(sequenceNumber);
        byte[] eotBuffer = eot.getUDPData();
        DatagramPacket eotPacket = new DatagramPacket(eotBuffer, eotBuffer.length, hostAddress, senderPort);
        receiverSocket.send(eotPacket);
        System.out.println("Debug:Send EOT " + sequenceNumber);
    }

    private void sendACK(int sequenceNumber) throws IOException {
        GBNPacket ack = new GBNPacket(GBNPacket.DATA_PACKET, sequenceNumber, new String());

        byte[] ackBuffer = ack.getUDPData();
        DatagramPacket ackPacket = new DatagramPacket(ackBuffer, ackBuffer.length, hostAddress, senderPort);
        receiverSocket.send(ackPacket);
        System.out.println("Debug:Send ACK " + sequenceNumber);
    }

    private void writeToFile(GBNPacket p) {
        try {
            System.out.println("Debug:Write to file " + p.getSequenceNumber());
            output.write(p.getData());
        } catch (IOException e) {
            System.err.println("I/O exception while writing to file!!!");
        }
    }
}