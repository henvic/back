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

	public ReceiverTCP(User destination, boolean running) throws IOException {
		super(destination, running);
		ServerSocket welcome = new ServerSocket(this.getPORT());
		this.receiverSocket = welcome.accept();

	}

	public void run() {

		while (true) {
			try {

				BufferedReader tcpBuffer = null;

				if(this.isRunning()) {
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
