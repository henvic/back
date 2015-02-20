package application.address;
	
import java.net.InetAddress;

import application.address.facade.Fachada;
import application.address.model.UserBTP;
import application.address.util.RepositoryUser;
import application.address.view.IpEditDialogController;
import application.address.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view\\RootLayout.fxml"));
//			loader.setLocation(getClass().getResource("view\\IpEditDialog.fxml"));
//			loader.setLocation(getClass().getResource("view\\ClientChat.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			
			Stage currentStage = new Stage();
			currentStage.setScene(new Scene(root));
			currentStage.setWidth(400);
			currentStage.setHeight(300);
			currentStage.setTitle("Wellcome");
			
			RootLayoutController rootController = loader.getController();
			rootController.setSelfStage(currentStage);
			
			currentStage.showAndWait();
			if(!rootController.isConnectClicked()){
				System.exit(0);
			}
			Fachada fachada = new Fachada(rootController.getConexao());
			fachada.getServer().addUser(new UserBTP("Layon", "172.22.22.23"));
			fachada.getServer().addUser(new UserBTP("vaca", "2"));
//			fachada.getServer().addUser(new UserBTP("Layon", "3"));
//			fachada.getServer().addUser(new UserBTP("Layon", "1"));
			fachada.getServer().addUser(new UserBTP("mario", "5"));
			fachada.getServer().addUser(new UserBTP("pedogoy", "10"));
			
			UserBTP userDefault = new UserBTP("Eu man", InetAddress.getLocalHost().getHostAddress()); 
			
			FXMLLoader loader2 = new FXMLLoader();
			loader2.setLocation(getClass().getResource("view\\IpEditDialog.fxml"));
			AnchorPane root2 = (AnchorPane) loader2.load();
			primaryStage.setScene(new Scene(root2));
			IpEditDialogController editDialog = loader2.getController();
			editDialog.enfiarParametro(fachada.getServer().getControlUsers().getUsers(), userDefault, primaryStage);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private Object isOkClicked() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
//	currentStage.setSloader.getController();
//	currentStage.initOwner(primaryStage);
	
//	RepositoryUser users = new RepositoryUser();

//	RootLayoutController controller = loader.getController();
//	primaryStage.showAndWait();
//	return controller.isConnectClicked();
//	IpEditDialogController vaca = loader.getController();
	
//	vaca.enfiarParametro(users);
//	Scene scene = new Scene(root,600,400);
//	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//	primaryStage.setScene(scene);
//	primaryStage.show();
}
