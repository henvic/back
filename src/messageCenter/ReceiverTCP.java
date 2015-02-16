package messageCenter;

import modules.Receiver;
import modules.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by leo on 28/01/2015.
 */
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
			// TODO Auto-generated catch block
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
					System.out.println(this.getDestination().getIp() + " falou: " + new String(tcpBuffer.readLine()));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
