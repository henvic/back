package application.address.model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class SenderUDP extends Sender {
	private DatagramSocket senderDSocket;

	public SenderUDP(User destination, DatagramSocket datagram) throws IOException {
		super(destination);
		// TODO Auto-generated constructor stub
		this.senderDSocket = datagram;

	}

	public void send(byte[] data) throws IOException {
		byte[] dataBytes = data;
		DatagramPacket packet;
		packet = new DatagramPacket(dataBytes, dataBytes.length, destinationIP, PORT);
		this.senderDSocket.send(packet);
		
	}

}
