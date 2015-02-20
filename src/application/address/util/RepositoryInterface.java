package application.address.util;

import java.util.Vector;

public interface RepositoryInterface<T> {
	
	public abstract void add(T newElement);
	public abstract void remover(String key);
	public abstract boolean exist(String key);
	public abstract int findIndex(String key);
	public abstract Vector<T> getElements();
	
}