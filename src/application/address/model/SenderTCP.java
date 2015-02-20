package application.address.model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class SenderTCP extends Sender {
	private Socket senderSocket;

	public SenderTCP(User destination, boolean udp) throws IOException {
		super(destination);
		// TODO Auto-generated constructor stub
		this.senderSocket = new Socket(InetAddress.getByName(destination.getIp().toString()), Sender.PORT);

	}

	@Override
	public void send(byte[] data) throws IOException {
		DataOutputStream outputStream = new DataOutputStream(this.senderSocket.getOutputStream());
		outputStream.write(data);		
	}
	
	

}
