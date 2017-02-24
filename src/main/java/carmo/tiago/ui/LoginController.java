package carmo.tiago.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Login Controller.
 * 
 * @author Tarun Tyagi
 */
public class LoginController implements Initializable {
	@FXML
	private TextField userId;
	@FXML
	private PasswordField password;
	@FXML
	private Label errorMessage;

	@FXML
	protected void processLogin() {
		try {
			if (!LoginApp.getInstance().userLogging(userId.getText(), password.getText())) {
				errorMessage.setText("Username/password combination is invalid.");
			}
		} catch (Exception e) {
			errorMessage.setText("Username/password combination is invalid.");
		}
	}

	@FXML
	protected void processAddUser() {
		LoginApp.getInstance().goToAddUser();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		userId.setPromptText("username");
		password.setPromptText("password");
	}
}
