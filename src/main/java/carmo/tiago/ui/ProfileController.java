package carmo.tiago.ui;

import java.net.URL;
import java.util.ResourceBundle;

import carmo.tiago.persistence.UserEntity;
import carmo.tiago.services.UserServices;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 * Java FX FXML Controller.
 * 
 * @author Tarun Tyagi
 */
public class ProfileController implements Initializable {
	@FXML
	private TextField user;
	@FXML
	private TextField name;
	@FXML
	private TextField email;
	@FXML
	private Label success;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		UserEntity loggedUser = LoginApp.getInstance().getLoggedUser();
		user.setText(loggedUser.getName());
		if (loggedUser.getEmail() != null) {
			email.setText(loggedUser.getEmail());
		}
		if (!loggedUser.getName().equals(null)) {
			name.setText(loggedUser.getName());
		}
		success.setOpacity(0);
	}

	@FXML
	protected void processLogout() {
		LoginApp.getInstance().userLogout();
	}

	@FXML
	protected void processUpdate() {
		System.out.println("Entrou");
		UserEntity loggedUser = LoginApp.getInstance().getLoggedUser();
		try {
			UserServices.updateUser(loggedUser, email.getText(), name.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		animateMessage();
	}

	private void animateMessage() {
		FadeTransition ft = new FadeTransition(new Duration(3000), success);
		ft.setFromValue(0.0);
		ft.setToValue(1);
		ft.play();
	}
}
