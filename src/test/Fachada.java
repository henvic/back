package test;

import java.util.Scanner;

public class Fachada {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		String escolha = entrada.next();
		
		if(escolha.equals("UDP")){
			new UDP().run();
		} else {
			new TCP().run();
		}
		
		
		entrada.close();
	}

}
