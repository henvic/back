package protocol;

import messageCenter.ReceiverBTP;
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
    public Sender client;

    public BackTP(UserBTP source, UserBTP destination, boolean running) throws IOException {
		this.client = new Sender(destination, running);

		this.sourceUser = source;

		this.server = new Thread(new ReceiverBTP(destination, running));
		this.server.start();


    }

    public void sendText(String text) throws IOException {
		client.send(text);
	}
}
