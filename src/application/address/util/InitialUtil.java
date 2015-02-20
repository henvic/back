package application.address.util;

import application.address.model.User;

public class InitialUtil {
	String ipUser;
	String nomeUser;
	String ipServer;
	String conexao;
	
	public InitialUtil(String ipUser,String nomeUser,String ipServer,String conexao) {
		this.ipUser = ipUser;
		this.nomeUser = nomeUser;
		this.ipServer = ipServer;
		this.conexao =conexao;
	}
}
