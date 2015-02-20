package application.address.util;

public class InvalidDataException extends Exception {
	public InvalidDataException(String message) {
		super("Preencha os campos corretamente");
	}

}
