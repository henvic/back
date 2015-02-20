package application.address.business;

import application.address.model.User;
import application.address.util.RepositoryUser;

public class BusinessUser {
	private RepositoryUser users;
	
	public BusinessUser() {
		this.users = new RepositoryUser();
	}
	
	public void addUser(User user){
		if(!exist(user.getIp())){
			users.add(user);
		}
	}
	
	public void removeUser(String ip){
		if(!exist(ip)){
			throw new NullPointerException("Cliente n�o existente");
		}else{			
			users.remove(ip);
		}
	}
	
	public boolean exist(String ip){
		return users.exist(ip);		
	}
}
