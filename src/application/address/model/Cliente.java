package application.address.model;

import protocol.Back;
import protocol.BackTP;
import protocol.Packet;

import java.io.IOException;
import java.net.DatagramSocket;

public class Cliente {

	private User me;
	private Back protocol;

	private int port = 50000;

	public Cliente(User me, String protocolo, String serverIp) throws IOException {
		this.me = me;

		if (protocolo.equalsIgnoreCase("tcp")){

		} else {
			protocol = new BackTP((UserBTP) me, new UserBTP ("server", serverIp), new DatagramSocket(port));
			protocol.send(meToString().getBytes(), "nothing.");
		}
	}

	public void receive(Packet p) {
		if (p.getType().equals("p2p.....")) {
	}

	public String meToString() {
		return me.getUsername() + "\n"
				+ me.getIp() +"\n";
	}
}
