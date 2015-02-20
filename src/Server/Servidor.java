package Server;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import protocol.Back;
import protocol.Packet;
import application.address.business.BusinessUser;
import application.address.model.User;
import application.address.util.BeckSocket;

public abstract class Servidor {
	private BusinessUser controlUsers;
	private int port;
	private InetAddress serverIp;
	private Back protocol;
	
	public Servidor(String protocolo) throws UnknownHostException{
		
		this.port = 8080;
		this.controlUsers = new BusinessUser();
		this.serverIp = InetAddress.getLocalHost();
		
		if(protocolo.equalsIgnoreCase("tcp")){
			//icializar o protocolo TCP
			
		}else{
			//icializar o protocolo UDP
			
		}
	}
	
	public void conectarAoServidor(User user) throws SocketException{
		
		BeckSocket beck = new BeckSocket(port, user.getIp());
//		protocol.se
	}
	
	public void redirecionarPorta(){
		
	}
	
	public abstract void conectarP2P(User user);
	
	public abstract void setStatus(String onOrOff);
	
	public abstract void desconectar(User user);
		
	public abstract void checkUsers();
	
	public abstract void addUser(User user);
	
	public abstract void removeUser(User user);	

}
