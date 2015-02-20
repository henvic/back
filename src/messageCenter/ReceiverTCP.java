package messageCenter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.Stream;

import application.address.model.Receiver;
import application.address.model.User;
import protocol.Packet;

public class ReceiverTCP extends Receiver implements Runnable {

	private Socket receiverSocket;
	private ServerSocket welcome;
	private InputStream tcpBuffer;

	public ReceiverTCP(boolean running) throws IOException {
		super(running);
		welcome = new ServerSocket(this.getPORT()+1);
		System.err.println("SOU UMA PUTA");
		receiverSocket = welcome.accept();
		tcpBuffer = receiverSocket.getInputStream();

	}

	public void run() {
		File saida = null;
		FileOutputStream saidaII = null;
		while (true) {
			try {
				
				if(this.isRunning()) {
					//TCP buffer + receivez
					int  tamanho = 0;
					while((tamanho = tcpBuffer.available()) == 0){
						//mantém programa parado até ter dados para serem lidos
					}
					byte[] dados = new byte[tamanho];
					tcpBuffer.read(dados);
					Packet packet = receivePacket(dados);
					if(packet.getType().equals("default.")){
						System.err.println( new String(packet.getData()));
					} else {
						saida = new File("downloads/" + "Padrão." + packet.getType().substring(0, packet.getType().indexOf('.')));
						saidaII = new FileOutputStream(saida);
						int tam = packet.getData().length;
						saidaII.write(packet.getData());
						saidaII.flush();

						while((tamanho = tcpBuffer.available()) != 0){
							dados = new byte[tamanho];
							tcpBuffer.read(dados);
							saidaII.write(dados);
							saidaII.flush();
						}
						saidaII.close();
						System.out.println("y");
					}
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
	
}
