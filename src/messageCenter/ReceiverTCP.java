package messageCenter;

import modules.Receiver;
import modules.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.Stream;

import protocol.Packet;

public class ReceiverTCP extends Receiver implements Runnable {

	private Socket receiverSocket;
	private ServerSocket welcome;
	private BufferedReader tcpBuffer;

	public ReceiverTCP(User destination, boolean running) throws IOException {
		super(destination, running);
		welcome = new ServerSocket(this.getPORT());
		receiverSocket = welcome.accept();
		tcpBuffer = new BufferedReader(new InputStreamReader(receiverSocket.getInputStream()));

	}

	public void run() {
		File saida = null;
		FileOutputStream saidaII = null;
		while (true) {
			try {
				
				if(this.isRunning()) {
					//TCP buffer + receivez
					String tipo = "";
					int  tamanho = 0;
					tcpBuffer.readLine();
					tipo = tcpBuffer.readLine();
					tcpBuffer.readLine();
					tcpBuffer.readLine();
					tcpBuffer.readLine();
					tamanho = Integer.parseInt(tcpBuffer.readLine());
					if(tipo.equals("default.")){
						char[] mensagem = new char[tamanho];
						tcpBuffer.read(mensagem, 0, tamanho);
						String msg = new String(mensagem);
						System.out.println(msg);
					} else {
					/*	saida = new File("downloads/" + "Padrão." + tipo);
						saidaII = new FileOutputStream(saida);
						char[] mensagem = new char[tamanho];
						tcpBuffer.read(mensagem, 0, tamanho);
						byte[] msg = toBytes(mensagem);
						saidaII.write(msg);
						saidaII.flush();
						saidaII.close();
					*/	BufferedWriter buf = new BufferedWriter(new FileWriter("downloads/" + "Padrão." + tipo));
						String dados;
						while((dados = tcpBuffer.readLine()) != null){
							System.out.println(dados);
							buf.write(dados);
							buf.close();
						}
						
						
					}
					tcpBuffer = new BufferedReader(new InputStreamReader(receiverSocket.getInputStream()));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	public Packet receivePacket (String dataString) {

		int firstParam = dataString.indexOf("\n");

		int secondParam = dataString.indexOf("\n", firstParam+1);

		int thirdParam = dataString.indexOf("\n", secondParam+1);

		int param4 = dataString.indexOf("\n", thirdParam+1);

		int param5 = dataString.indexOf("\n", param4+1);

		int param6 = dataString.indexOf("\n", param5+1 );

		return new Packet(Integer.parseInt(dataString.substring(0, firstParam)), dataString.substring(firstParam + 1, secondParam), Integer.parseInt(dataString.substring(secondParam + 1, thirdParam)), Integer.parseInt(dataString.substring(thirdParam+1, param4)), Boolean.parseBoolean(dataString.substring(param4 + 1, param5)), dataString.substring(param6+1).getBytes(), Integer.parseInt(dataString.substring(param5 + 1, param6)));

	}
	
	private byte[] toBytes(char[] chars) {
	    CharBuffer charBuffer = CharBuffer.wrap(chars);
	    ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
	    byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
	            byteBuffer.position(), byteBuffer.limit());
	    Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
	    Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
	    return bytes;
	}
}
