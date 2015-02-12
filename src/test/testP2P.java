package test;

import userCenter.UserBTP;
import protocol.BackTP;
import modules.User;
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
        User user = new UserBTP("<nome aqui>");
        user.setIp("172.20.4.5");

		//criar classe tcp e escolher entre BTP ou TCP
        try {
            BackTP protocol = new BackTP(user, true, true);
            boolean running = true;

            while(running){
                System.out.println("eu aqui");
                String message = in.nextLine();

                if (message.equals("quit")) {
                    running = false;
                    protocol.server.interrupt();
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
