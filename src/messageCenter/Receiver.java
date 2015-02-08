package messageCenter;

import modules.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by leo on 28/01/2015.
 */
public class Receiver extends Thread implements Runnable {

    private boolean running;
	private boolean tcp;
    private User destination;
    private DatagramSocket receiverDSocket;
	private Socket receiverSocket;
    private static int PORT = 2000;
    private static int BUFFER_SIZE = 1500;

    public Receiver(User destination, boolean running, boolean tcp) throws IOException {
        this.running = running;
        this.destination = destination;
		this.tcp = tcp;
		if(tcp) {
			ServerSocket welcome = new ServerSocket(PORT);
			this.receiverSocket = welcome.accept();
		} else {
        	this.receiverDSocket = new DatagramSocket(PORT);
		}

    }

	public void run() {
        while (true) {
            //UDP buffer
			byte[] udpBuffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(udpBuffer, udpBuffer.length);


			try {

				BufferedReader tcpBuffer = null;

                if(running) {
					if(tcp) {
						//TCP buffer + receive
						tcpBuffer = new BufferedReader(new InputStreamReader(receiverSocket.getInputStream()));
						System.out.println(destination.getIP() + " falou: " + new String(tcpBuffer.readLine()));

					} else {
						//UDP receive
						this.receiverDSocket.receive(packet);
						System.out.println(destination.getIP() + " falou: " + new String(packet.getData()));
						packet.setData(new byte[BUFFER_SIZE]);

						//envia ack
					}
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
