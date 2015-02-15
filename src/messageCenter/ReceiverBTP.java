package messageCenter;

import modules.Receiver;
import modules.User;
import protocol.Packet;

import java.io.*;
import java.net.*;

/**
 * Created by leo on 28/01/2015.
 */
public class ReceiverBTP extends Receiver implements Runnable {

	private DatagramSocket receiverSocket;
	public static byte[] arquivo;

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
					Packet p = receivePacket(packet.getData());
					if(p.getFileType().equals("default")){
						System.err.println(this.getDestination().getIp() + " falou: " + new String( p.getData()));

					} else {
						//trocar o "Padrão." por um nome padrão incremental para arquivos recebidos
						
						System.err.println(p);
					}

					//n sei o que é isso, by Deyv
					packet.setData(new byte[this.getBufferSize()]);
					//envia ack
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public Packet receivePacket (byte[] data) {
		String dataString = new String(data);

		int firstParam = dataString.indexOf("\n");

		int secondParam = dataString.indexOf("\n", firstParam+1);

		int thirdParam = dataString.indexOf("\n", secondParam+1);

		int param4 = dataString.indexOf("\n", thirdParam+1);

		int param5 = dataString.indexOf("\n", param4+1);

		int param6 = dataString.indexOf("\n", param5+1 );

		return new Packet(Integer.parseInt(dataString.substring(0, firstParam)), dataString.substring(firstParam + 1, secondParam), Integer.parseInt(dataString.substring(secondParam + 1, thirdParam)), Integer.parseInt(dataString.substring(thirdParam+1, param4)), Boolean.parseBoolean(dataString.substring(param4 + 1, param5)), dataString.substring(param6+1).getBytes(), Integer.parseInt(dataString.substring(param5 + 1, param6)));

	}
}
