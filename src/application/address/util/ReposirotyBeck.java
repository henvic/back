package application.address.util;

import java.util.Vector;

public class ReposirotyBeck implements RepositoryInterface<BeckSocket> {
	private Vector<BeckSocket> becks;
	
	@Override
	public void add(BeckSocket newElement) {
		this.becks.add(newElement);		
	}

	@Override
	public void remover(String key) {
		boolean achou = false;
		for(int i = 0; i < becks.size() && !achou;i++){
			if(becks.get(i).getIpDestino().equals(key)){
				becks.remove(i);
				achou = true;
			}
		}
		
	}

	@Override
	public boolean exist(String key) {
		boolean retorno = false;
		for (int i = 0; i < becks.size() && !retorno; i++) {
			if(becks.elementAt(i).getIpDestino().equals(key)){
				retorno = true;
			}
		}
		return false;
	}

	@Override
	public int findIndex(String key) {
		int retorno = -1;
		for (int i = 0; i < becks.size() && retorno == -1; i++) {
			if(becks.elementAt(i).getIpDestino().equals(key)){
				retorno = i;
			}
		}
		return retorno;
	}

	@Override
	public Vector<BeckSocket> getElements() {
		return becks;
	}
}
