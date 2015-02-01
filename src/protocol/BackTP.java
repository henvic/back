package protocol;

import messageCenter.Receiver;
import messageCenter.Sender;
import modules.User;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class BackTP {
    public Thread server;
    public Sender client;

    public BackTP(User destination, boolean running) throws SocketException, UnknownHostException {
        server = new Thread(new Receiver(destination, running));
        server.start();

        client = new Sender(destination);
    }

    public void sendText(String text) throws IOException {
        client.send(text.getBytes());
    }
}
