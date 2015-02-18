package protocol;

import messageCenter.ReceiverBTP;
import modules.Receiver;
import modules.Sender;
import userCenter.UserBTP;

import java.io.IOException;

import com.sun.security.ntlm.Client;

public class BackTP implements Back {
	/**
	 * link between source user and receiver/sender class
	 * or
	 * one attribute: user, the user link the receiver and sender < this better
	 */

	public UserBTP sourceUser;
	public Thread server;
	private ReceiverBTP receiver;
	public Sender client;

	public Thread getServer() {
		return server;
	}

	public void setServer(Thread server) {
		this.server = server;
	}

	private int fileNumber;
	private int nextSeq;

	public BackTP(UserBTP source, UserBTP destination) throws IOException {
		this.client = new Sender(destination, true);

		this.sourceUser = source;
		this.receiver = new ReceiverBTP(destination, true);
		this.server = new Thread(receiver);
		this.server.start();

		this.fileNumber = 0;
	}

	public void sendText(String text) throws IOException {
		send(text.getBytes(), "default");
	}

	public void send(byte[] data, String fileExtension) throws IOException {
		//em fase de test
		int tam = receiver.getBufferSize();
		int qtdPacotes = data.length/tam + 1;
		for(int i = 0; i < qtdPacotes-1; i++){
			byte[] dados = new byte[tam];
			for(int j = 0; j < tam; j++) dados[j] = data[j + i*tam];
			Packet p = new Packet(this.fileNumber, fileExtension, nextSeq, i*tam, false, dados, data.length);
			client.send(getPacketBytes(p));
			nextSeq += 1;
		}
		byte[] dados = new byte[tam];
		int fim = data.length%tam;
		for(int j = 0; j < fim; j++) dados[j] = data[j + (qtdPacotes-1)*tam];
		Packet p = new Packet(this.fileNumber, fileExtension, nextSeq, (qtdPacotes-1)*tam, true, dados, fim);
		client.send(getPacketBytes(p));
		this.fileNumber += 1;
		nextSeq += 1;

	}
	
	

	public byte[] getPacketBytes(Packet p){
		return p.getBytes();
	}

	/**
	 * method: sends message
	 * param ip: destinationIp
	 * other param to flags (ack, openServer, connectionRequest...)
	 */


	/**
	 * method: process the ack receiving
	 * description: Update nextSeq number
	 */
	public void receiveACK() {

	}

}
