package carmo.tiago.ui;

import java.net.URL;
import java.util.ResourceBundle;
import carmo.tiago.services.UserPOJO;
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
public class HomePageController implements Initializable {
	@FXML
	private TextField user;
	@FXML
	private TextField name;
	@FXML
	private TextField email;
	@FXML
	private TextField sex;
	@FXML
	private TextField age;
	@FXML
	private TextField password;
	@FXML
	private TextField password2;
	@FXML
	private TextField height;
	@FXML
	private TextField weight;
	@FXML
	private TextField activityLevel;
	@FXML
	private Label success;
	@FXML
	private Label error;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		UserPOJO loggedUser = LoginApp.getInstance().getLoggedUser();
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
		if (password.equals(password2)) {
			try {
				UserServices.updateUser(name.getText(), email.getText(), password.getText(), age.getText(),
						activityLevel.getText(), sex.getText(), height.getText(), weight.getText());
				animateMessage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
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
