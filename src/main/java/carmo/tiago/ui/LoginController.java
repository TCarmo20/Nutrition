package carmo.tiago.ui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/**
 * 
 * @author Tiago Carmo
 *
 */
public class LoginController implements Initializable {

	@FXML
	private BorderPane Login;

	@FXML
	private Group groupLogin;

	@FXML
	private Label errorMessage;

	@FXML
	private JFXTextField userIdLogin;

	@FXML
	private JFXPasswordField passwordLogin;

	@FXML
	private JFXButton login;

	@FXML
	private JFXButton addUserLogin;

	@FXML
	protected void processLogin() {
		fadeMessage();
		try {
			LoginApp.getInstance().userLogging(userIdLogin.getText(), passwordLogin.getText());
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

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		userIdLogin.setPromptText("Email");
		passwordLogin.setPromptText("Password");

	}
}
