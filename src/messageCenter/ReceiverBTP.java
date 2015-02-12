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
public class ReceiverBTP extends Receiver implements Runnable {

    private DatagramSocket receiverSocket;

    public ReceiverBTP(User destination, boolean running) throws IOException {
		super(destination, running);
        this.receiverSocket = new DatagramSocket(this.getPORT());

    }

	public void run() {
        while (true) {
            //UDP buffer
			byte[] udpBuffer = new byte[this.getBufferSize()];
            DatagramPacket packet = new DatagramPacket(udpBuffer, udpBuffer.length);

			try {
                if(this.isRunning()) {
					//UDP receive
					this.receiverSocket.receive(packet);
					System.out.println(this.getDestination().getIp() + " falou: " + new String(packet.getData()));
					packet.setData(new byte[this.getBufferSize()]);

					//envia ack
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
