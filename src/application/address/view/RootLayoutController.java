package application.address.view;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;

import org.controlsfx.dialog.Dialogs;

import application.address.model.User;
import application.address.model.UserBTP;

import com.sun.media.sound.InvalidDataException;

import javafx.fxml.FXML;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RootLayoutController {
	@FXML
	private TextField fieldNome;
	@FXML
	private TextField fieldIp;
	@FXML private RadioMenuItem conexaoTCP;
	@FXML private RadioMenuItem conexaoUDP;
	private String userName;
	private String ip;
	private String serverIp;
	private boolean connectClicked;
	private Stage selfStage;
	private String conexao;
	
	public RootLayoutController() {
		// TODO Auto-generated constructor stub
	}
	
	public void setSelfStage(Stage selfStage) {
		this.selfStage = selfStage;
	}

	public User retorno(){
		User retorno = new UserBTP(this.userName, this.ip);
		return retorno;		
	}
	
	@FXML
	public void initialize(){
		conexao = "tcp";
		conexaoTCP.selectedProperty().addListener((observable,oldValue, newValue) -> {
			if(newValue){
				conexao = "tcp";
			}else{
				conexao = "udp";
			}
		});
		
	}
	@FXML
	private void connect(){
		Exception exp = null;
		if(!fieldNome.getText().equals("") && !fieldIp.getText().equals("")){
			try {
				InetAddress address = InetAddress.getByName(fieldIp.getText());
				this.ip = address.getHostAddress();
				this.serverIp = fieldIp.getText();
				this.userName = fieldNome.getText();
				connectClicked = true;
			} catch (UnknownHostException e) {
				exp = new Exception("Ip inválido");
			}
		}else{
			try {
				throw new InvalidDataException();
			} catch (InvalidDataException e) {
				exp = e;
			}
		}
		if(exp != null){
			Dialogs.create()
			.title("Erro")
			.message(exp.getMessage())
			.showWarning();
		}
		this.selfStage.close();
	}


	public boolean isConnectClicked() {
		return connectClicked;
	}

	public String getUserName() {
		return userName;
	}

	public String getIp() {
		return ip;
	}

	public String getServerIp() {
		return serverIp;
	}

	public String getConexao() {
		return conexao;
	}

	
}
