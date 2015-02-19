package Server;

import java.util.Vector;
import application.address.model.User;

public abstract class Servidor {
		
	public Servidor(){
		
	}
		
	public abstract void checkUsers();
	
	public abstract void addUser(User user);
	
	public abstract void removeUser(User user);
	
	

}
