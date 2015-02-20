package application.address.view;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RootLayoutController {
	@FXML
	private TextField fieldNome;
	@FXML
	private TextField fieldIp;
	
	private boolean connectClicked = false;
	
	public RootLayoutController() {
		// TODO Auto-generated constructor stub
	}
	
	@FXML
	private void connect(){
		try {
			InetAddress address = InetAddress.getByName(fieldIp.getText());
			if(fieldIp.getText().equals("")){
				
			}else{
				// servidor connect
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	
	public Object isConnectClicked() {
		return connectClicked;
	}

}
