package Server;

import java.net.Socket;
import java.util.Vector;
import application.address.model.User;

public class ServerTCP extends Servidor {
	Vector<Socket> users;

	public ServerTCP() {
		this.users = new Vector<Socket>();
		
	}

	@Override
	public void checkUsers() {
		
	}

	@Override
	public void addUser(User user) {
	}

	@Override
	public void removeUser(User user) {
		users.remove(user);
	}

}
