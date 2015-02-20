package application.address.util;

import java.util.Vector;

import application.address.model.User;

public class RepositoryUser implements RepositoryInterface<User>{
	private Vector<User> users;
	
	public RepositoryUser() {
		this.users = new Vector<User>();
	}


	@Override
	public void add(User newElement) {
		this.users.add(newElement);
	}

	@Override
	public void remover(String key) {
		boolean achou = false;
		for(int i = 0; i < users.size() && !achou;i++){
			if(users.get(i).getIp().equals(key)){
				users.remove(i);
				achou = true;
			}
		}
		
	}

	@Override
	public Vector<User> getElements() {
		return users;
	}

	@Override
	public boolean exist(String key) {
		boolean retorno = false;
		for (int i = 0; i < users.size() && !retorno; i++) {
			if(users.elementAt(i).getIp().equals(key)){
				retorno = true;
			}
		}
		return false;
	}

	@Override
	public int findIndex(String key) {
		int retorno = -1;
		for (int i = 0; i < users.size() && retorno == -1; i++) {
			if(users.elementAt(i).getIp().equals(key)){
				retorno = i;
			}
		}
		return retorno;
	}
}
