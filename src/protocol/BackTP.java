package protocol;

import messageCenter.ReceiverBTP;
import modules.Receiver;
import modules.Sender;
import userCenter.UserBTP;

import java.io.IOException;

public class BackTP {
	/**
	 * link between source user and receiver/sender class
	 * or
	 * one attribute: user, the user link the receiver and sender < this better
	 */

	public UserBTP sourceUser;
	public Thread server;
	private ReceiverBTP receiver;
	public Sender client;

	private int fileNumber;
	private int nextSeq;

	public BackTP(UserBTP source, UserBTP destination, boolean running) throws IOException {
		this.client = new Sender(destination, running);

		this.sourceUser = source;
		this.receiver = new ReceiverBTP(destination, running);
		this.server = new Thread(receiver);
		this.server.start();

		this.fileNumber = 0;
	}

	public void sendText(String text) throws IOException {
		send(text.getBytes(), "default");
	}

	public void send(byte[] data, String fileExtension) throws IOException {
		if (data.length > receiver.getBufferSize()){
			//fragmenta
		} else {
			Packet p = new Packet(this.fileNumber, fileExtension, nextSeq, data);
			client.send(getPacketBytes(p));
		}
	}

	private byte[] getPacketBytes(Packet p){
		return p.toString().getBytes();
	}

	/**
	 * method: sends message
	 * param ip: destinationIp
	 * other param to flags (ack, openServer, connectionRequest...)
	 */
	public void sendMessage(String destinationIp) { // precisa?

	}

	/**
	 * method: process the ack receiving
	 * description: Update nextSeq number
	 */
	public void receiveACK() {

	}

}
