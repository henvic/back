package application.address.util;

import java.net.DatagramSocket;
import java.net.SocketException;

public class BeckSocket extends DatagramSocket{
	private String ipDestino;
	
	public BeckSocket(int port, String ipDestino) throws SocketException {
		super(port);
		this.ipDestino = ipDestino;
	}

	public String getIpDestino() {
		return ipDestino;
	}

}
