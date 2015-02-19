package application.address.view;

import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

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

    private ObservableList<User> users; 

    private Stage dialogStage;
    private boolean okClicked = false;
    private RepositoryUser usersRep;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    public IpEditDialogController(RepositoryUser usersRep) {
		users = FXCollections.observableArrayList();
		this.usersRep = usersRep;
	}
    @FXML
    private void initialize() {
    	
    	nameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsername());
    	IpColumn.setCellValueFactory(cellData -> cellData.getValue().getIp());
    	userTable.setItems(users);
    	userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showUserDetails(newValue));
    }

    private Object showUserDetails(User newValue) {
		return null;
	}
    
    private void thread(){
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
				IteratorUser iterator = (IteratorUser) usersRep.getIterator();
				while(iterator.hasNext()){
					users.add(iterator.next());
				}
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

//        firstNameField.setText(person.getFirstName());
//        lastNameField.setText(person.getLastName());
//        streetField.setText(person.getStreet());
//        postalCodeField.setText(Integer.toString(person.getPostalCode()));
//        cityField.setText(person.getCity());
//        birthdayField.setText(DateUtil.format(person.getBirthday()));
//        birthdayField.setPromptText("dd.mm.yyyy");
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
//            person.setFirstName(firstNameField.getText());
//            person.setLastName(lastNameField.getText());
//            person.setStreet(streetField.getText());
//            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
//            person.setCity(cityField.getText());
//            person.setBirthday(DateUtil.parse(birthdayField.getText()));

            okClicked = true;
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