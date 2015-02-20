package application.address.facade;

import java.io.IOException;

import Server.Servidor;
import application.address.util.RepositoryUser;

public class Fachada {
	private Servidor server;

	Fachada(String protocolo) throws IOException{
		this.server = new Servidor(protocolo);
	}
	
	public void conectarUsuario(){
		
	}
	
	public void adicionarUsuario(){
		
	}
	
//	public void
}
