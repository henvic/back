package application.address.model;

import messageCenter.ReceiverBTP;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

import application.address.model.User;

public class Sender {

    private User destination;
    private InetAddress destinationIP;
    private DatagramSocket senderDSocket;
	private Socket senderSocket;
    private boolean counter;
	private boolean tcp;
    private static int PORT = 2000;

    public Sender(User destination, boolean tcp) throws IOException {
        this.destination = destination;
        this.destinationIP = InetAddress.getByName(destination.getIp().toString());
		this.tcp = tcp;
		if (!tcp) {
			this.senderSocket = new Socket(destinationIP, PORT);
		} else {
			this.senderDSocket = new DatagramSocket();
		}

    }

    public void send(byte[] data) throws IOException {
		if (!tcp) {

			DataOutputStream outputStream = new DataOutputStream(this.senderSocket.getOutputStream());
			outputStream.write(data);
			System.out.print("chegou - sender");
		} else {
			byte[] dataBytes = data;
			DatagramPacket packet;
			packet = new DatagramPacket(dataBytes, dataBytes.length, destinationIP, PORT);
			this.senderDSocket.send(packet);
			//caso queira testar o tamanho do arquivo enviado e o local, descomentar as linhas abaixo e na classe testP2P
			//o array[] b � onde esta o arquivo, no m�todo basta perceber que foi recebido um arquivo e salva-lo na pasta do projeto
/*			String dataString = new String(data);
			int firstParam = dataString.indexOf("\n");
			int secondParam = dataString.indexOf("\n", firstParam+1);
			int thirdParam = dataString.indexOf("\n", secondParam+1);
			int param4 = dataString.indexOf("\n", thirdParam+1);
			int param5 = dataString.indexOf("\n", param4+1);
			int param6 = dataString.indexOf("\n", param5+1);
			byte[] b = dataString.substring(param6+1).getBytes();
			System.out.println(b.length);
	*/		
		}

    }

}
