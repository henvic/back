package protocol;

import java.io.IOException;

import messageCenter.ReceiverBTP;
import messageCenter.ReceiverTCP;
import modules.Sender;
import userCenter.UserBTP;
import userCenter.UserTCP;

public class BackTCP implements Back {
	
	public UserTCP sourceUser;
	public Thread server;
	private ReceiverTCP receiver;
	public Sender client;

	private int fileNumber;
	private int nextSeq;

	public BackTCP(UserTCP source, UserTCP destination, boolean running) throws IOException {
		this.client = new Sender(destination, running);

		this.sourceUser = source;
		this.receiver = new ReceiverTCP(destination, running);
		this.server = new Thread(receiver);
		this.server.start();

		this.fileNumber = 0;
	}

	@Override
	public void sendText(String text) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(byte[] data, String fileExtension) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getPacketBytes(Packet p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(String destinationIp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveACK() {
		// TODO Auto-generated method stub
		
	}
	

}
