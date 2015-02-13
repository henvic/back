package modules;

import modules.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

/**
 * Created by leo on 28/01/2015.
 */
public class Sender {

    private User destination;
    private InetAddress destinationIP;
    private DatagramSocket senderDSocket;
	private Socket senderSocket;
    private boolean counter;
	private boolean tcp;
    private static int PORT = 2000;

    public Sender(User destination, boolean tcp) throws IOException {
        this.destination = destination;
        this.destinationIP = InetAddress.getByName(destination.getIp());
		this.tcp = tcp;
		if (!tcp) {
			this.senderSocket = new Socket(destinationIP, PORT);
		} else {
			this.senderDSocket = new DatagramSocket();
		}

    }

    public void send(String data) throws IOException {
		if (!tcp) {

			this.senderSocket = new Socket(destinationIP, PORT);
			DataOutputStream outputStream = new DataOutputStream(this.senderSocket.getOutputStream());
			outputStream.writeBytes(data);
		} else {
			byte[] dataBytes = data.getBytes();
			DatagramPacket packet;
			packet = new DatagramPacket(dataBytes, dataBytes.length, destinationIP, PORT);
			this.senderDSocket.send(packet);
		}

    }

}
