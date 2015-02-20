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
			users.addUser(user);
		}
	}
	
	public void removeUser(String ip){
		if(!exist(ip)){
			throw new NullPointerException("Cliente não existente");
		}else{			
			users.removeUser(ip);
		}
	}
	
	public boolean exist(String ip){
		return users.exist(ip);		
	}
}
