package messageCenter;

import protocol.BackTP;
import protocol.Packet;

import java.io.*;
import java.net.*;

import application.address.model.Receiver;
import application.address.model.User;
import application.address.model.UserBTP;

public class ReceiverBTP extends Receiver implements Runnable {
	private DatagramSocket receiverSocket;
	public static byte[] arquivo;
	private BackTP protocol;
	private int ACK;
	private final int MOD = 1000;
	UserBTP destination;

	public ReceiverBTP(boolean running, BackTP protocol, DatagramSocket datagram, UserBTP destination) throws IOException {
		super(running);
		this.receiverSocket = datagram;
		this.protocol = protocol;
		this.ACK = 0;
		this.destination = destination;
	}

	public void run() {
		File saida = null;
		FileOutputStream saidaII = null;

		while (true) {
			//UDP buffer
			byte[] udpBuffer = new byte[this.getBufferSize() + Packet.HEADER_SIZE];
			DatagramPacket packet = new DatagramPacket(udpBuffer, udpBuffer.length);

			try {
				if(this.isRunning()) {
					//UDP receive
					this.receiverSocket.receive(packet);
					//cria pacote
					System.out.println("puta");
					Packet p = receivePacket(packet.getData());
					System.out.println(p);
					if(p.getSeqNumber() == ACK){
						ACK += 1;
						ACK = ACK%MOD;
						if (p.getType().equals("default.")) {
							//dataLength = tamanho real dos dados
							System.err.println(" falou: " + new String(getBytes(packet.getData(), p.getDataLength())));
							protocol.receiveText(p);

						} else if (p.getType().equals("........")){
							protocol.receiveSignal(p.getData());
						} else if (p.getType().equals("ACK.....")){
							ACK -= 1;
							if(protocol.ackPosition <= p.getSeqNumber()){
								protocol.ackPosition = p.getSeqNumber();
							}
							continue;
						} else {
							//trocar o "Padrão." por um nome padrão incremental para arquivos recebidos
							if(p.getOffset() == 0 &&  !p.getType().equals("default.")){
								saida = new File("downloads/" + "Padrão." + p.getType().substring(0, p.getType().indexOf(".")));
								saidaII = new FileOutputStream(saida);
							}


							if(p.getDataLength() < p.getOffset() + getBufferSize()){
								//dataLength = tamanho real dos dados
								byte[] b = getBytes(packet.getData(), p.getDataLength());

								saidaII.write(b);
								System.out.println(new String(b));
								saidaII.flush();
								saidaII.close();

								p.setData(b);
							} else {
								//dataLength = tamanho do arquivo
								//tamanho do data = tamanho do buffer
								byte[] b = getBytes(packet.getData());
								saidaII.write(b);
								saidaII.flush();
							}

							protocol.receiveFile(p);

						}

						//reseta buffer BTP
						packet.setData(new byte[this.getBufferSize()]);
						System.out.println(this.getBufferSize());
					} 
					Packet ack = new Packet(ACK);
					receiverSocket.send(new DatagramPacket(ack.getBytes(), ack.getBytes().length, InetAddress.getByName(destination.getIp()), receiverSocket.getLocalPort()));
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
				dataString.substring(length + 1).getBytes(),
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
			retorno[i] = data[i+ Packet.HEADER_SIZE];
		}
		return retorno;
	}

	public byte[] getBytes(byte[] data){
		byte[] retorno = new byte[data.length - Packet.HEADER_SIZE];
		for(int i = 0; i < data.length - Packet.HEADER_SIZE; i++){
			retorno[i] = data[i + Packet.HEADER_SIZE];
		}
		return retorno;
	}

}
