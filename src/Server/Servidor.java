package Server;

import java.util.Vector;

import modules.User;

public abstract class Servidor {
		
	public Servidor(){
		
	}
		
	public abstract void checkUsers();
	
	public abstract void addUser(User user);
	
	public abstract void removeUser(User user);
	
	

}
