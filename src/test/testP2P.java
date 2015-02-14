package test;

import userCenter.UserBTP;
import protocol.BackTP;
import modules.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by leo on 01/02/2015.
 */
public class testP2P {
    public static void main (String[] args) {
        Scanner in = new Scanner(System.in);
		User me = new UserBTP("leo", "LocalHost");
        User user = new UserBTP("<nome aqui>", "LocalHost"); //destino
        
        try {
            BackTP protocol = new BackTP((UserBTP)me, (UserBTP)user, true);
            boolean running = true;

            while(running){
                System.out.println("eu aqui");
                String message = in.nextLine();

                if (message.equals("quit")) {
                    running = false;
                    protocol.server.interrupt();
                   //tentar enviar arquivo
                } else if(message.startsWith("Arquive:")){
                	File arq = new File(message.substring(message.indexOf(" ")+1));
        			FileInputStream file = new FileInputStream(arq);
        			byte[] sendData = new byte[(int) arq.length()];
        			file.read(sendData);
        			//caso queira testar o tamanho do arquivo enviado e o local, descomentar a linha abaixo e na classe testP2P
        			//System.err.println(sendData.length);
                    protocol.send(sendData,message.substring(message.indexOf('.')+1));
                } else {
                    protocol.sendText(message);
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
