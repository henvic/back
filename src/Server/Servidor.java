package Server;

import java.io.IOException;
import java.net.*;

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

public abstract class Servidor {
	private BusinessUser controlUsers;
	//identificacao de servidor p/ clientes (beckSocket)
	private int port;
	private InetAddress serverIp;

	//thread
	private boolean running;
	private DatagramSocket receiverSocket;
	private ReceiverBTP receiver;

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
								conectarAoServidor(new UserBTP(split[0], split[1]));
							} else {
								//usuario ja conectado
							}
							//envia "shake" com datagramSocket

						}
					}
				}
			}).start();
		}
	}
	
	public void conectarAoServidor(User user) { //recebe porta?? e a thread /\ incrementa port?
		controlUsers.addUser(user);
		try {
			BeckSocket beck = new BeckSocket(port, user.getIp());
		} catch (SocketException e) {
			e.printStackTrace();
		}

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
