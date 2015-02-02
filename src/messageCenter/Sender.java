package messageCenter;

import modules.User;

import java.io.IOException;
import java.net.*;

/**
 * Created by leo on 28/01/2015.
 */
public class Sender {

    private User destination;
    private InetAddress destinationIP;
    private DatagramSocket senderSocket;
    private boolean counter;
    private static int PORT = 2000;

    public Sender(User destination) throws UnknownHostException, SocketException {
        this.destination = destination;
        this.destinationIP =InetAddress.getByName(destination.getIP());
        this.senderSocket = new DatagramSocket();

    }

    public void send(byte[] data) throws IOException {
        DatagramPacket packet;
        packet = new DatagramPacket(data, data.length, destinationIP, PORT);
        this.senderSocket.send(packet);

    }

}
