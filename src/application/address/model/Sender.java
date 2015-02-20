package application.address.model;

import messageCenter.ReceiverBTP;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

import application.address.model.User;

public abstract class Sender {

	private User destination;
	protected InetAddress destinationIP;
	private boolean counter;
	protected static int PORT = 2000;

	public Sender(User destination) throws IOException {
		this.destination = destination;
		this.destinationIP = InetAddress.getByName(destination.getIp().toString());
	}

	public abstract void send(byte[] data) throws IOException;



}
