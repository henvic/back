package application.address.view;

import java.util.Observable;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import application.address.facade.Fachada;
import application.address.model.User;
import application.address.util.IteratorUser;
import application.address.util.RepositoryUser;

/**
 * Dialog to edit details of a person.
 * 
 * @author Marco Jakob
 * */
public class IpEditDialogController {

	@FXML
	private TableView<User> userTable;
	@FXML
	private TableColumn<User, String> nameColumn;
	@FXML
	private TableColumn<User, String> IpColumn;
	@FXML
	private TextField postalCodeField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField birthdayField;
	@FXML private TextField userName;
	@FXML private TextField localIp;
	@FXML private RadioButton onRadioButton;
	@FXML private RadioButton offRadioButton;
	private ObservableList<User> usersList = FXCollections
			.observableArrayList();

	private Stage dialogStage;
	private boolean okClicked = false;
	private RepositoryUser usersRep;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	public IpEditDialogController() {
		// users = FXCollections.observableArrayList();
	}

	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getUsername()));
		IpColumn.setCellValueFactory((cellData -> new SimpleStringProperty(
				cellData.getValue().getIp())));
		userTable.setItems(usersList);
		userTable
		.getSelectionModel()
		.selectedItemProperty()
		.addListener(
				(observable, oldValue, newValue) -> showUserDetails(newValue));
	}

	public void enfiarParametro(RepositoryUser users, User eu, Stage stage){
		this.userName.setText(eu.getUsername());
		this.dialogStage = stage;
		this.localIp.setText(eu.getIp());
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					Platform.runLater(new Runnable() {						
						@Override
						public void run() {
							User selected = userTable.getSelectionModel().getSelectedItem();
							usersList.setAll(users.getElements());
//							System.out.println(users.getElements().size());
							userTable.getSelectionModel().select(selected);;
						}
					});
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	private Object showUserDetails(User newValue) {
		return null;
	}

	// private void thread(){
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// while(true){
	// IteratorUser iterator = (IteratorUser) usersRep.getIterator();
	// while(iterator.hasNext()){
	// users.add(iterator.next());
	// }
	// try {
	// Thread.sleep(200);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// }).start();
	// }

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 * 
	 * @param person
	 */
	public void setPerson() {

		// firstNameField.setText(person.getFirstName());
		// lastNameField.setText(person.getLastName());
		// streetField.setText(person.getStreet());
		// postalCodeField.setText(Integer.toString(person.getPostalCode()));
		// cityField.setText(person.getCity());
		// birthdayField.setText(DateUtil.format(person.getBirthday()));
		// birthdayField.setPromptText("dd.mm.yyyy");
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			userTable.getSelectionModel().getSelectedItem();
			okClicked = true;
			//fazer a conexão p2p, tenho 2 usuários aqui.
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		return false;
	}
}