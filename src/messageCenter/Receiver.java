package messageCenter;

import modules.User;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by leo on 28/01/2015.
 */
public class Receiver extends Thread implements Runnable {

    private boolean running;
    private User destination;
    private DatagramSocket receiverSocket;
    private static int PORT = 2000;

    public Receiver(User destination, boolean running) throws SocketException {
        this.running = running;
        this.destination = destination;
        this.receiverSocket = new DatagramSocket(PORT);

    }

    public void run() {
        //metodo que vai receber a msg (escrevendo na tela)
        //com buffer e etc
    }
}
