package test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCP implements Runnable{
	
	
	public void run(){
		System.out.println("Olá, vc sera cliente ou servidor?");
		Scanner in = new Scanner(System.in);
		String tipo = in.next();
		if(tipo.equals("cliente")){
			
			try {
				Socket sock = new Socket("Localhost", 2000);
				//codigo de envio;
				while(true){
					String msg = in.nextLine();
					DataOutputStream socketOut = new DataOutputStream(sock.getOutputStream());
					socketOut.writeBytes(msg);
				}
				
				
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} else {
			
			
			
		}
		
	}
}
