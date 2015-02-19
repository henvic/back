package application.address.view;

import application.address.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ClientChatController {
	@FXML
	private TextArea chatHistory;

	@FXML
	private TextArea chatSend;

	@FXML
	private Canvas photo;

	@FXML
	private Main mainApp;
	
	private StringBuffer buffer;


	public ClientChatController() {
		this.buffer = new StringBuffer();
		
	}

	@FXML
	private void initialize() {
	}

	
	
	@FXML
	private void send() {
//		chatSend.setOnKeyPressed(new EventHandler<KeyEvent>()
//			    {
//	        @Override
//	        public void handle(KeyEvent ke)
//	        {
//	            if (ke.getCode().equals(KeyCode.ENTER))
//	            {
//	            	if(buffer.length() > 1000){
//	            		buffer = new StringBuffer();
//	            	}
	            	String text = chatSend.getText();
	        		chatHistory.setText(buffer.append(text+ "\n").toString());
	        		chatSend.clear();
//	            }
//	        }
//	    });
		
//		System.out.println(chatHistory.getText());
	}


}
