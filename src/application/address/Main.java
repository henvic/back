package application.address;
	
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
//			currentStage.setSloader.getController();
//			currentStage.initOwner(primaryStage);
			
			RepositoryUser users = new RepositoryUser();
			users.add(new UserBTP("Layon", "1"));
			users.add(new UserBTP("cao", "1"));
			users.add(new UserBTP("Deyv", "1"));
			users.add(new UserBTP("Leo", "1"));
			users.add(new UserBTP("Nada", "1"));
			
			RootLayoutController rootController = loader.getController();
			rootController.setSelfStage(currentStage);
//			RootLayoutController controller = loader.getController();
			
			currentStage.showAndWait();
			if(!rootController.isConnectClicked()){
				System.exit(0);
			}
			FXMLLoader loader2 = new FXMLLoader();
			loader2.setLocation(getClass().getResource("view\\IpEditDialog.fxml"));
			AnchorPane root2 = (AnchorPane) loader2.load();
			primaryStage.setScene(new Scene(root2));
			primaryStage.show();
//			primaryStage.showAndWait();
//			return controller.isConnectClicked();
//			IpEditDialogController vaca = loader.getController();
			
//			vaca.enfiarParametro(users);
//			Scene scene = new Scene(root,600,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
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
}
