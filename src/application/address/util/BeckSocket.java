package application.address.util;

import Server.Servidor;
import application.address.business.BusinessUser;
import application.address.model.User;
import application.address.model.UserBTP;
import protocol.BackTP;
import protocol.Packet;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class BeckSocket extends DatagramSocket{
	private User destino;
	private BackTP protocol;

	private Servidor server;
	
	public BeckSocket(int port, User destino, Servidor server) throws IOException {
		super(port);
		this.destino = destino;
		//protocol = new BackTP(new UserBTP("server", InetAddress.getLocalHost().getHostName()), (UserBTP) destino, this); // envia dados para classe receive qnd comecar com p2p
	}

	public String getIpDestino() {
		return destino.getIp();
	}

	public void receive (Packet p) throws IOException {
		if (p.getType().equals("p2p.....")) {
			//String[] split = new String(protocol.getReceiver().getBytes(p.getData(), p.getDataLength())).split("\n");
			//String secondUser = split[0];
			//server.conectarP2P(destino, server.getControlUsers().getUser(secondUser));
		} else if (p.getType().equals("p2pok...")) {
			server.setP2pStatus(1);
		} else if (p.getType().equals("p2pno...")) {
			server.setP2pStatus(-1);
		} else if (p.getType().equals("list....")){
			//protocol.send(server.getControlUsers().get);
		}
	}

}
