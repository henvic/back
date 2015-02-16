package protocol;

import java.io.IOException;

public interface Back {
	public Thread getServer();
	public void sendText(String text) throws IOException;
	public void send(byte[] data, String fileExtension) throws IOException;
	public byte[] getPacketBytes(Packet p);
	public void receiveACK();
}
