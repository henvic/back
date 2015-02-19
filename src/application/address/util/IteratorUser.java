package application.address.util;

import java.util.Vector;

import application.address.model.User;

public class IteratorUser implements Iterator<User> {
	private Vector<User> users;
	int index;

	public IteratorUser(Vector<User> users) {
		this.users = users;
		this.index = 0;
	}
	@Override
	public boolean hasNext() {
		boolean retorno = false;
		if(index < users.size() || users.get(index) != null){
			retorno = true;
		}
		return retorno;
	}

	@Override
	public User next() {
		User retorno = users.get(index);
		return retorno;
	}

}
