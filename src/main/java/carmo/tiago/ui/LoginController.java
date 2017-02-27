package carmo.tiago.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

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
		fadeMessage();
		try {
			if (!LoginApp.getInstance().userLogging(userId.getText(), password.getText())) {
				errorMessage.setText("Username/password combination is invalid.");
				animateMessage(errorMessage);
			}
		} catch (Exception e) {
			errorMessage.setText("Username/password combination is invalid.");
			animateMessage(errorMessage);
		}
	}

	private void fadeMessage() {
		errorMessage.setText("");
	}

	private void animateMessage(Label message) {
		FadeTransition ft = new FadeTransition(new Duration(1000), message);
		ft.setFromValue(0.0);
		ft.setToValue(1);
		ft.play();
	}

	@FXML
	protected void processAddUser() {
		LoginApp.getInstance().goToAddUser();
	}

	@FXML
	protected void processExit() {
		LoginApp.getInstance().exitApp();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		userId.setPromptText("Email");
		password.setPromptText("Password");
	}
}
