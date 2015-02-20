package protocol;

import messageCenter.ReceiverBTP;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import application.address.model.Receiver;
import application.address.model.Sender;
import application.address.model.SenderUDP;
import application.address.model.UserBTP;

public class BackTP implements Back {

	// sender and receiver attributes
	public UserBTP sourceUser;
	public Thread server;
	private ReceiverBTP receiver;
	public SenderUDP client;

	// GBN attributes
	private int fileNumber; //packet id
	private int nextSeq;	//next sequence number
	private LinkedList<Packet> buffer; //unAckedPackets

	private final int SUCCESS_RATE = 20;

	public Thread getServer() {
		return server;
	}

	public BackTP(UserBTP source, UserBTP destination) throws IOException {
		this.client = new SenderUDP(destination);

		this.sourceUser = source;
		this.receiver = new ReceiverBTP( true, this);
		this.server = new Thread(receiver);
		this.server.start();

		this.fileNumber = 0;
		this.nextSeq = 0;
		buffer = new LinkedList<Packet>();
	}

	//chama o metodo send* passando como file extension 'default', o que significa texto normal
	public void sendText(String text) throws IOException {
		send(text.getBytes(), "default");
	}

	//fragmenta pacotes e salva na janela
	public void send(byte[] data, String fileExtension) throws IOException {
		//em fase de test
		int tam = receiver.getBufferSize();
		int qtdPacotes = (data.length/tam) + 1;
		//'salva' todos os pacotes menos o ultimo (last packet = false)
		for(int i = 0; i < qtdPacotes-1; i++){

			byte[] dados = new byte[tam];

			for(int j = 0; j < tam; j++) {
				dados[j] = data[j + i*tam];
			}

			Packet p = new Packet(this.fileNumber, fileExtension, nextSeq, i*tam, false, dados, data.length);
			buffer.addLast(p);
//			client.send(getPacketBytes(p));
			nextSeq += 1;
		}

		//'salva' ultimo pacote (last packet = true)
		byte[] dados = new byte[tam];
		int fim = data.length%tam;

		for(int j = 0; j < fim; j++) {
			dados[j] = data[j + (qtdPacotes-1)*tam];
		}

		Packet p = new Packet(this.fileNumber, fileExtension, nextSeq, (qtdPacotes-1)*tam, true, dados, fim);
		buffer.addLast(p);
		//client.send(getPacketBytes(p));
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
