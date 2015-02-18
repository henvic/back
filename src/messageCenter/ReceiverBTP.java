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
		File saida = null;
		FileOutputStream saidaII = null;
		
		while (true) {
			//UDP buffer
			byte[] udpBuffer = new byte[this.getBufferSize()+47];
			DatagramPacket packet = new DatagramPacket(udpBuffer, udpBuffer.length);

			try {
				if(this.isRunning()) {
					//UDP receive
					this.receiverSocket.receive(packet);
					Packet p = receivePacket(packet.getData());
					if(p.getFileType().equals("default.")){
						System.err.println(this.getDestination().getIp() + " falou: " + new String( getBytes(packet.getData(), p.getDataLength())));

					} else {
						//trocar o "Padrão." por um nome padrão incremental para arquivos recebidos
						if(p.getOffset() == 0 &&  !p.getFileType().equals("default.")){
							saida = new File("downloads/" + "Padrão." + p.getFileType().substring(0, p.getFileType().indexOf(".")));
							saidaII = new FileOutputStream(saida);
						}
						
						
						if(p.getDataLength() <= p.getOffset() + getBufferSize()){
							/*p = receivePacket(packet.getData(), p.getDataLength());
							saidaII.write(p.getData());
							System.out.println(p);
							System.out.println(p.getData().length);
							*/
							byte[] b = getBytes(packet.getData(), p.getDataLength());
							saidaII.write(b);
							System.out.println(new String(b));
							saidaII.flush();
							saidaII.close();
						} else {
							byte[] b = getBytes(packet.getData());
							saidaII.write(b);
							//saidaII.write(p.getData());
							saidaII.flush();
						}
						//System.err.println(p);
						
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
		
		int fileNumber = dataString.indexOf("\n");
		int fileType = dataString.indexOf("\n", fileNumber+1);
		int id = dataString.indexOf("\n", fileType+1);
		int offset = dataString.indexOf("\n", id+1);
		int isFinal = dataString.indexOf("\n", offset+1);
		int length = dataString.indexOf("\n", isFinal+1);

		return new Packet(Integer.parseInt(dataString.substring(0, fileNumber)),
            dataString.substring(fileNumber + 1, fileType),
            Integer.parseInt(dataString.substring(fileType + 1, id)),
            Integer.parseInt(dataString.substring(id+1, offset)),
            Boolean.parseBoolean(dataString.substring(offset + 1, isFinal)),
            dataString.substring(length+1).getBytes(),
            Integer.parseInt(dataString.substring(isFinal + 1, length))
        );

	}
	public Packet receivePacket (byte[] data, int dataFinal) {
		System.err.println(data.length + ":p");
		String dataString = new String(data);
		System.out.println(dataString.length());
		int fileNumber = dataString.indexOf("\n");
		int fileType = dataString.indexOf("\n", fileNumber+1);
		int id = dataString.indexOf("\n", fileType+1);
		int offset = dataString.indexOf("\n", id+1);
		int isFinal = dataString.indexOf("\n", offset+1);
		int length = dataString.indexOf("\n", isFinal+1);

		return new Packet(Integer.parseInt(dataString.substring(0, fileNumber)),
            dataString.substring(fileNumber + 1, fileType),
            Integer.parseInt(dataString.substring(fileType + 1, id)),
            Integer.parseInt(dataString.substring(id+1, offset)),
            Boolean.parseBoolean(dataString.substring(offset + 1, isFinal)),
            dataString.substring(length+1, dataFinal+length+3).getBytes(),
            Integer.parseInt(dataString.substring(isFinal + 1, length)));

	}
	
	public byte[] getBytes(byte[] data, int dataFinal){
		byte[] retorno = new byte[dataFinal];
		for(int i = 0; i < dataFinal; i++){
			retorno[i] = data[i+47];
		}
		return retorno;
	}
	
	public byte[] getBytes(byte[] data){
		byte[] retorno = new byte[data.length-47];
		for(int i = 0; i < data.length-47; i++){
			retorno[i] = data[i+47];
		}
		return retorno;
	}
	
}
