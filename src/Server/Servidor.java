package Server;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;

import application.address.model.Receiver;
import application.address.model.Sender;
import application.address.model.UserBTP;
import messageCenter.ReceiverBTP;
import protocol.Back;
import protocol.BackTP;
import protocol.Packet;
import application.address.business.BusinessUser;
import application.address.model.User;
import application.address.util.BeckSocket;

public class Servidor {
	public BusinessUser getControlUsers() {
		return controlUsers;
	}

	private BusinessUser controlUsers;
	//identificacao de servidor p/ clientes (beckSocket)
	private int port;
	private InetAddress serverIp;

	//global threads
	private boolean running;

	//thread
	private DatagramSocket receiverSocket;
	private ReceiverBTP receiver;

	//array de threads
	private LinkedList<BeckSocket> becks;

	//identificacao do servidor p/ clientes (conectar)
	private final int PORT = 50000;

	public void setP2pStatus(int p2pstatus) {
		this.p2pStatus = p2pstatus;
	}

	public int p2pStatus;
	public Servidor(String protocolo) throws IOException {
		
		this.port = 8080;
		this.controlUsers = new BusinessUser();
		this.serverIp = InetAddress.getLocalHost();

		this.running = true;
		this.receiverSocket = new DatagramSocket(PORT);
		this.receiver = new ReceiverBTP(true, null, null, null);

		this.p2pStatus = 0;

		if(protocolo.equalsIgnoreCase("tcp")){
			//icializar o receiver TCP
			
		}else{
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						byte[] udpBuffer = new byte[receiver.getBufferSize() + Packet.HEADER_SIZE];
						DatagramPacket packet = new DatagramPacket(udpBuffer, udpBuffer.length);

						while (running) {
							//UDP receive
							try {
								receiverSocket.receive(packet);
							} catch (IOException e) {
								e.printStackTrace();
							}

							//cria pacote
							Packet p = receiver.receivePacket(packet.getData());
							//o username vem na primeira linha, o ip na segunda
							String[] split = new String(receiver.getBytes(p.getData(), p.getDataLength())).split("\n");

							//conecta ao servidor se usuario ainda n estiver no repositorio de beckSockets
							if (!controlUsers.exist(split[1])) {
								conectarAoServidor(new UserBTP(split[0], split[1]), port);
							} else {
								//usuario ja conectado
							}
							//envia "shake" com datagramSocket
							Packet shake = new Packet(port + "");
							try {
								receiverSocket.send(new DatagramPacket(shake.getBytes(), receiver.getBufferSize()));
							} catch (IOException e) {
								e.printStackTrace();
							}

							port += 1;
						}
					}
				}
			}).start();

		}
	}
	
	public void conectarAoServidor(User user, int port) {
		controlUsers.addUser(user);
		try {
			BeckSocket beck = new BeckSocket(port, user, this);
			//adiciona beck no vetor
			becks.addLast(beck);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void redirecionarPorta(){
		
	}
	
	public void conectarP2P(User source, User destination) throws IOException {
		//envia destination requisicao
		BackTP serverTo2 = new BackTP (new UserBTP("server", InetAddress.getLocalHost().getHostName()), (UserBTP) destination);
		serverTo2.send(source.getIp().getBytes(),"p2p....."); //envia ip do user1 pro user2
		//espera resposta
		while (p2pStatus == 0) {

		}

		//se positiva manda source iniciar conexao
		if(p2pStatus == 1) {
			BackTP serverTo1 = new BackTP (new UserBTP("server", InetAddress.getLocalHost().getHostName()), (UserBTP) source);
			serverTo1.send(destination.getIp().getBytes(),"p2popen."); //envia ip do user2 pro user1
		}
	}
	
	public void setStatus(String onOrOff){

	}
	
	public void desconectar(User user){

	}
		
	public void checkUsers(){

	}
	
	public void addUser(User user){

	}
	
	public void removeUser(User user){

	}

}
