package application.address.facade;

import application.address.util.RepositoryUser;

public class Fachada {
	private RepositoryUser repositorio;
	Fachada(String protocolo){
		this.repositorio = new RepositoryUser();
		if(protocolo.equals("tcp")){
			//inicializar negocio tcp
		}else{
			//inizializar negocio udp
		}
	}
	
	public void conectarUsuario(){
		
	}
	
	public void adicionarUsuario(){
		
	}
	
//	public void
}
