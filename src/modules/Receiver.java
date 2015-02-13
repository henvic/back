package modules;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

/**
 * Created by leo on 11/02/2015.
 * ljsa at cin.ufpe.br
 */
public abstract class Receiver {

	private boolean running;
	private User destination;
	private static final int PORT = 2000;
	private static final int BUFFER_SIZE = 1500;

	public Receiver(User destination, boolean running) throws IOException {
		this.running = running;
		this.destination = destination;

	}

	public static int getPORT() {
		return PORT;
	}

	public static int getBufferSize() {
		return BUFFER_SIZE;
	}

	public User getDestination() {
		return destination;
	}

	public boolean isRunning() {
		return running;
	}
}
