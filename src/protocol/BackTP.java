package protocol;

import messageCenter.ReceiverBTP;

import java.util.Random;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.xml.stream.events.StartDocument;

import application.address.model.Receiver;
import application.address.model.Sender;
import application.address.model.SenderUDP;
import application.address.model.UserBTP;

public class BackTP implements Back {

	// sender and receiver attributes
	private final int PORT = 2000;
	public UserBTP sourceUser;
	public Thread server;
	private ReceiverBTP receiver;
	public SenderUDP client;
	public int ackPosition;
	public final int WINDOW = 8;
	public Packet[] buffer;
	private int ponteiro;
	// GBN attributes
	private int fileNumber; //packet id
	private int nextSeq;	//next sequence number
	private DatagramSocket datagram;
	private final int SUCCESS_RATE = 50;
	private long timeStart;
	public Thread getServer() {
		return server;
	}

	public BackTP(UserBTP source, UserBTP destination) throws IOException {
		this.datagram = new DatagramSocket(PORT, InetAddress.getByName(destination.getIp()));
		this.ackPosition = 0;
		this.client = new SenderUDP(destination, datagram);
		this.sourceUser = source;
		this.receiver = new ReceiverBTP(true, this, datagram);
		this.server = new Thread(receiver);
		this.server.start();

		this.fileNumber = 0;
		this.nextSeq = 0;
		this.buffer = new Packet[WINDOW] ;
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
			while(buffer[WINDOW-1] != null){
				if(buffer[0].getSeqNumber() < ackPosition){
					deslocar(ackPosition - buffer[0].getSeqNumber());
				} else if(System.currentTimeMillis() - this.timeStart > 400) {
					for(int k = 0; k < WINDOW && buffer[k] != null; k++){
						this.ponteiro = k;
						new Thread(new Runnable() {
							public void run() {
								try {
									if((int)(Math.random()*100) > SUCCESS_RATE){
										client.send(getPacketBytes(buffer[ponteiro]));
									}else{
										System.out.println("deu erro");
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}).start();
					}
				}
			}
			this.timeStart = System.currentTimeMillis();
			if((int)(Math.random()*100) > SUCCESS_RATE){
				client.send(getPacketBytes(p));
			}else{
				System.out.println("deu erro");
			}
			nextSeq += 1;
		}

		//'salva' ultimo pacote (last packet = true)
		byte[] dados = new byte[tam];
		int fim = data.length%tam;

		for(int j = 0; j < fim; j++) {
			dados[j] = data[j + (qtdPacotes-1)*tam];
		}

		Packet p = new Packet(this.fileNumber, fileExtension, nextSeq, (qtdPacotes-1)*tam, true, dados, fim);
		while(buffer[WINDOW-1] != null){
			if(buffer[0].getSeqNumber() < ackPosition){
				deslocar(ackPosition - buffer[0].getSeqNumber());
			} else if(System.currentTimeMillis() - this.timeStart > 400) {
				for(int i = 0; i < WINDOW && buffer[i] != null; i++){
					this.ponteiro = i;
					new Thread(new Runnable() {
						public void run() {
							try {
								if((int)(Math.random()*100) > SUCCESS_RATE){
									client.send(getPacketBytes(buffer[ponteiro]));
								}else{
									System.out.println("deu erro");
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}).start();
				}
			}
		}
		new Thread(new Runnable() {
			public void run() {
				try {
					if((int)(Math.random()*100) > SUCCESS_RATE){
						client.send(getPacketBytes(p));
					}else{
						System.out.println("deu erro");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		this.timeStart = System.currentTimeMillis();
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

	public void deslocar(int x){
		for(int i = 0; i<x; i++){
			for(int j = 0; j<WINDOW-1; j++) buffer[j] = buffer[j+1];
			buffer[WINDOW-1] = null;
		}
	}

	/**
	 * method: process the ack receiving
	 * description: Update nextSeq number
	 */
	public void receiveACK() {

	}

}
