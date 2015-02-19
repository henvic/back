package protocol;

import java.io.IOException;

import messageCenter.ReceiverBTP;
import messageCenter.ReceiverTCP;
import modules.Sender;
import userCenter.UserBTP;
import userCenter.UserTCP;

public class TCP implements Back {
	
	public UserTCP sourceUser;
	public Thread server;
	private ReceiverTCP receiver;
	public Sender client;

	private int fileNumber;
	private int nextSeq;

	public TCP(UserTCP source, UserTCP destination) throws IOException {

		this.sourceUser = source;
		this.fileNumber = 0;

		//this.client = new Sender(destination, false);

		this.receiver = new ReceiverTCP(destination, true);
		this.server = new Thread(receiver);
		this.server.start();
	}

	@Override
	public void sendText(String text) throws IOException {
		send(text.getBytes(), "default");		
	}

	@Override
	public void send(byte[] data, String fileExtension) throws IOException {	//implementação semelhante ao UDP
		Packet p  = new Packet(this.fileNumber, fileExtension, nextSeq, 0, true, data, data.length );
		client.send(getPacketBytes(p));
		
	}

	@Override
	public byte[] getPacketBytes(Packet p) {	//implementar igual ao UDP
		return p.toString().getBytes();
	}


	@Override
	public void receiveACK() {	//não faz nada, já tem implementado implicitament no Socket		
	}

	public Thread getServer() {
		return server;
	}

	public void setServer(Thread server) {
		this.server = server;
	}

	

}
