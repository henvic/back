package messageCenter;

import modules.Receiver;
import modules.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import protocol.Packet;

public class ReceiverTCP extends Receiver implements Runnable {

	private Socket receiverSocket;
	private ServerSocket welcome;
	public ReceiverTCP(User destination, boolean running) throws IOException {
		super(destination, running);
		this.welcome = new ServerSocket(this.getPORT());
		

	}

	public void run() {
		
		try {
			this.receiverSocket = welcome.accept();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {

				BufferedReader tcpBuffer = null;
				System.out.println(1);
				if(this.isRunning()) {
					System.out.println(2);
					//TCP buffer + receive
					tcpBuffer = new BufferedReader(new InputStreamReader(receiverSocket.getInputStream()));
					System.out.println(this.getDestination().getIp() + " falou: " + tcpBuffer.readLine());
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	public Packet receivePacket (String dataString) {

		int firstParam = dataString.indexOf("\n");

		int secondParam = dataString.indexOf("\n", firstParam+1);

		int thirdParam = dataString.indexOf("\n", secondParam+1);

		int param4 = dataString.indexOf("\n", thirdParam+1);

		int param5 = dataString.indexOf("\n", param4+1);

		int param6 = dataString.indexOf("\n", param5+1 );

		return new Packet(Integer.parseInt(dataString.substring(0, firstParam)), dataString.substring(firstParam + 1, secondParam), Integer.parseInt(dataString.substring(secondParam + 1, thirdParam)), Integer.parseInt(dataString.substring(thirdParam+1, param4)), Boolean.parseBoolean(dataString.substring(param4 + 1, param5)), dataString.substring(param6+1).getBytes(), Integer.parseInt(dataString.substring(param5 + 1, param6)));

	}
}
