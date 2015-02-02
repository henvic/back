package messageCenter;

import modules.User;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by leo on 28/01/2015.
 */
public class Receiver extends Thread implements Runnable {

    private boolean running;
    private User destination;
    private DatagramSocket receiverSocket;
    private static int PORT = 2000;
    private static int BUFFER_SIZE = 1400;

    public Receiver(User destination, boolean running) throws SocketException {
        this.running = running;
        this.destination = destination;
        this.receiverSocket = new DatagramSocket(PORT);

    }

    public void run() {
        while (true) {
            byte[] data = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(data, data.length);

            try {
                this.receiverSocket.receive(packet);

                if(running) {
                    System.out.println(destination.getIP() + " falou: " + new String(packet.getData()));
                    packet.setData(new byte[BUFFER_SIZE]);

                    //envia ack
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
