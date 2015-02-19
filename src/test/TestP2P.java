package test;

import protocol.Back;
import protocol.TCP;
import protocol.BackTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import application.address.model.User;
import application.address.model.UserBTP;
import application.address.model.UserTCP;

public class TestP2P{
	
    public static void  main (String args[]) {
		while (true){
			Scanner in = new Scanner(System.in);
			//String tipo = in.nextLine();
			String tipo = in.next();
			User me = null;
			User user = null;
			if(tipo.equalsIgnoreCase("TCP")){
				me = new UserTCP("leo", "172.22.46.18");
				user = new UserTCP("<nome aqui>", "172.22.46.112");
				//baixa
			} else {
				me = new UserBTP("leo", "172.22.46.112");
				user = new UserBTP("<nome aqui>", "172.22.46.18"); //destino
			}
			try {
				Back protocol = null;
				if(me instanceof UserBTP){
					protocol = new BackTP((UserBTP)me, (UserBTP)user);
				} else {

					protocol = new TCP((UserTCP)me, (UserTCP)user);

				}
				boolean running = true;

				while(running){
					System.out.println("eu aqui");
					//System.out.println(System.currentTimeMillis());
					String message = "";
					if(message.equals("resend")){

					} else {
						message = in.nextLine();
					}

					if (message.equals("quit")) {
						running = false;
						//não sei o que fazer com a linha abaixo, favor conferir a classe back, talvez ela tenha que ser uma classe abstrata ao invés de uma interface
						protocol.getServer().interrupt();
					   //tentar enviar arquivo
					} else if(message.startsWith("Arquivo:")) {
						File arq = new File(message.substring(message.indexOf(" ") + 1));
						FileInputStream file = new FileInputStream(arq);
						byte[] sendData = new byte[(int) arq.length()];
						file.read(sendData);
						//caso queira testar o tamanho do arquivo enviado e o local, descomentar a linha abaixo e na classe testP2P
						//System.err.println(sendData.length);
						protocol.send(sendData, message.substring(message.indexOf('.') + 1));
					} else if (message.equals("reset")) {
						running = false;
					} else {
						System.out.println("Você enviou: "+ message);
						protocol.sendText(message);
						System.out.println("Você enviou: "+ message);
					}
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

    }
}
