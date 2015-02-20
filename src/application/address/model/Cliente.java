package application.address.model;

import application.address.util.BeckSocket;
import protocol.Back;
import protocol.BackTP;
import protocol.Packet;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Cliente {

	private User me;
	private BackTP protocolBTP;

	private String serverIp;

	private int port = 50000;

	public Cliente(User me, String protocolo, String serverIp) throws IOException {
		this.me = me;
		this.serverIp = serverIp;

		if (protocolo.equalsIgnoreCase("tcp")){

		} else {
			protocolBTP = new BackTP((UserBTP) me, new UserBTP ("server", serverIp), new DatagramSocket(port));
			protocolBTP.send(meToString().getBytes(), "nothing.");
		}
	}

	public void receive(Packet p) throws IOException {
		if (p.getType().equals("shake...")) {
			String[] split = new String(protocolBTP.getReceiver().getBytes(p.getData(), p.getDataLength())).split("\n");
			String port = split[0];
			this.port = Integer.parseInt(port);

			protocolBTP = new BackTP((UserBTP) me, new UserBTP ("server", serverIp), new DatagramSocket(this.port));

			protocolBTP.send("".getBytes(), "list....");//pede lista de usuario
		}
	}

	public String meToString() {
		return me.getUsername() + "\n"
				+ me.getIp() +"\n";
	}
}
