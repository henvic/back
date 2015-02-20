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
	private BusinessUser controlUsers;
	//identificacao de servidor p/ clientes (beckSocket)
	private int port;
	private InetAddress serverIp;

	//global threads
	private boolean running;

	//thread
	private DatagramSocket receiverSocket;
	private ReceiverBTP receiver;

	//thread garçom
	private LinkedList<BeckSocket> becks;
	private DatagramSocket receiverSocketG;
	private ReceiverBTP receiverG;

	//identificacao do servidor p/ clientes (conectar)
	private final int PORT = 50000;

	public Servidor(String protocolo) throws IOException {
		
		this.port = 8080;
		this.controlUsers = new BusinessUser();
		this.serverIp = InetAddress.getLocalHost();

		this.running = true;
		this.receiverSocket = new DatagramSocket(PORT);
		this.receiver = new ReceiverBTP(true, null);

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

			new Thread(new Runnable() {
				@Override
				public void run() {
					becks = new LinkedList<BeckSocket>();
					BeckSocket cur;
					while (true) {
						boolean run = true;

						if(!becks.isEmpty()) {
							cur = becks.getFirst();
							int i = 1;

							while (run && running) {
								boolean noErro = true;

								byte[] udpBuffer = new byte[receiverG.getBufferSize() + Packet.HEADER_SIZE];
								DatagramPacket packet = new DatagramPacket(udpBuffer, udpBuffer.length);

								try {
									cur.setSoTimeout(100);
									cur.receive(packet);
								} catch (SocketException e) {
									e.printStackTrace();
								} catch (IOException e) {
									System.err.println("nada a receber");
									noErro = false;
								}

								if(noErro) {
									
								}

								if (becks.iterator().hasNext()) {
									cur = becks.get(i);
								}

								if (cur == becks.getLast()) {
									run = false;
								}
							}
						}
					}
				}
			}).start();
		}
	}
	
	public void conectarAoServidor(User user, int port) {
		controlUsers.addUser(user);
		try {
			BeckSocket beck = new BeckSocket(port, user.getIp());
			//adiciona beck no vetor
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}
	
	public void redirecionarPorta(){
		
	}
	
	public void conectarP2P(User user){

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
