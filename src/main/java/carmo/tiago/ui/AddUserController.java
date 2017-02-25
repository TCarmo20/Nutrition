package carmo.tiago.ui;

import java.net.URL;
import java.util.ResourceBundle;

import carmo.tiago.services.UserServices;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class AddUserController implements Initializable {

	@FXML
	private TextField name;
	@FXML
	private TextField email;
	@FXML
	private TextField password;
	@FXML
	private TextField age;
	@FXML
	private TextField weight;
	@FXML
	private TextField height;
	@FXML
	private TextField activityLevel;
	@FXML
	private TextField sex;
	@FXML
	private Label success;
	@FXML
	private Label error;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		name.setPromptText("Name");
		email.setPromptText("Email");
		password.setPromptText("Password");
		success.setOpacity(0);
		error.setOpacity(0);
	}

	@FXML
	protected void goBack() {
		LoginApp.getInstance().gotoLogin();
	}

	@FXML
	protected void processAddUser() {
		try {
			UserServices.addUser(name.getText(), email.getText(), password.getText(), age.getText(),
					activityLevel.getText(), sex.getText(), height.getText(), weight.getText());
			animateMessage();
		} catch (Exception e) {
			animateErrorMessage();
		}
	}

	private void animateMessage() {
		FadeTransition ft = new FadeTransition(new Duration(3000), success);
		ft.setFromValue(0.0);
		ft.setToValue(1);
		ft.play();
	}

	private void animateErrorMessage() {
		FadeTransition ft = new FadeTransition(new Duration(3000), error);
		ft.setFromValue(0.0);
		ft.setToValue(1);
		ft.play();
	}

}
