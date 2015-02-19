package application.address.util;

import java.util.Vector;

import application.address.model.User;

public class RepositoryUser {
	private Vector<User> users;
	
	public RepositoryUser() {
		this.users = new Vector<User>();
	}
	
	public void addUser(User user){
		this.users.add(user);
	}
	
	public void removeUser(String Ip){
		boolean achou = false;
		for(int i = 0; i < users.size() && !achou;i++){
			if(users.get(i).getIp().equals(Ip)){
				users.remove(i);
				achou = true;
			}
		}
	}
	
	public Iterator<User> getIterator(){
		Iterator<User> iterator = new IteratorUser(this.users);
		return iterator;
	}
}
