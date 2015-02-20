package application.address.model;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public abstract class Receiver {

	private boolean running;
	private static final int PORT = 2000;
	private static final int BUFFER_SIZE = 1500;

	public Receiver(boolean running) throws IOException {
		this.running = running;

	}

	public static int getPORT() {
		return PORT;
	}

	public int getBufferSize() {
		return BUFFER_SIZE-47;
	}


	public boolean isRunning() {
		return running;
	}
}
