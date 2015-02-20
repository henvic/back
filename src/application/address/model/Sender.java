package application.address.model;

import messageCenter.ReceiverBTP;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

import application.address.model.User;

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
        this.destinationIP = InetAddress.getByName(destination.getIp().toString());
		this.tcp = tcp;
		if (!tcp) {
			this.senderSocket = new Socket(destinationIP, PORT+1);
		} else {
			this.senderDSocket = new DatagramSocket();
		}
    }

    public void send(byte[] data) throws IOException {
		if (!tcp) {

			DataOutputStream outputStream = new DataOutputStream(this.senderSocket.getOutputStream());
			outputStream.write(data);
		} else {
			byte[] dataBytes = data;
			DatagramPacket packet;
			packet = new DatagramPacket(dataBytes, dataBytes.length, destinationIP, PORT);
			
			this.senderDSocket.send(packet);
			
		}

    }

}
