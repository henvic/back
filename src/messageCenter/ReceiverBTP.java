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

					//System.out.println(this.getDestination().getIp() + " falou: " + new String(packet.getData()));

					File saida = new File("downloads/arq.jpeg");
					FileOutputStream saidaII = new FileOutputStream(saida);
					//flush prepara o arquivo para ser bufferizado(escrever os bytes nele)
					saidaII.flush();
					//write, escreve os bytes no arquivo
					saidaII.write(packet.getData());
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

		int param6 = dataString.indexOf("\n", param4+5);

		return new Packet(dataString.substring(0, firstParam), dataString.substring(firstParam + 1, secondParam), Integer.parseInt(dataString.substring(secondParam + 1, thirdParam)), Integer.parseInt(dataString.substring(thirdParam+1, param4)), Boolean.parseBoolean(dataString.substring(param4 + 1, param5)), dataString.substring(param6+1).getBytes());

	}
}
