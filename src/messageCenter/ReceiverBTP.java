package messageCenter;

import modules.Receiver;
import modules.User;

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

					File saida = new File("downloads/arq.txt");
					FileOutputStream saidaII = new FileOutputStream(saida);
					//flush prepara o arquivo para ser bufferizado(escrever os bytes nele)
					saidaII.flush();
					//write, escreve os bytes no arquivo
					saidaII.write(packet.getData());
					saidaII.flush();
					saidaII.close();
					packet.setData(new byte[this.getBufferSize()]);

					//envia ack
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
