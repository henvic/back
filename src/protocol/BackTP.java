package protocol;

import messageCenter.ReceiverBTP;

import java.io.IOException;
import java.util.Vector;

import application.address.model.Receiver;
import application.address.model.Sender;
import application.address.model.UserBTP;

public class BackTP implements Back {

	//sender and receiver attributes
	public UserBTP sourceUser;
	public Thread server;
	private ReceiverBTP receiver;
	public Sender client;

	private int fileNumber;
	private int nextSeq;

	public Thread getServer() {
		return server;
	}

	public BackTP(UserBTP source, UserBTP destination) throws IOException {
		this.client = new Sender(destination, true);

		this.sourceUser = source;
		this.receiver = new ReceiverBTP(destination, true, this);
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

	//menssagem de sinalizacao
	public void receiveSignal(byte[] data){

	}

	public void receiveFile(Packet p) {
		//send to other methods
	}

	public void receiveText(Packet p) {

	}

	/**
	 * method: process the ack receiving
	 * description: Update nextSeq number
	 */
	public void receiveACK() {

	}

}
