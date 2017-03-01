package carmo.tiago.ui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
	private TextField password2;
	@FXML
	private TextField age;
	@FXML
	private TextField weight;
	@FXML
	private TextField height;
	@FXML
	private JFXComboBox<String> activityLevel;
	@FXML
	private JFXComboBox<String> sex;
	@FXML
	private Label successMessage;
	@FXML
	private Label errorMessage;
	@FXML
	private Label nameErrorMessage;
	@FXML
	private Label emailErrorMessage;
	@FXML
	private Label passwordErrorMessage;
	@FXML
	private Label password2ErrorMessage;
	@FXML
	private JFXDatePicker agePicker;

	Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		name.setPromptText("Name");
		email.setPromptText("Email");
		password.setPromptText("Password");
		password2.setPromptText("Retype password");
		height.setPromptText("Height");
		weight.setPromptText("Weight");
		successMessage.setOpacity(0);
		errorMessage.setOpacity(0);
		sex.getItems().addAll("Male", "Female");
		activityLevel.getItems().addAll("Sedentário", "Atividade Leve", "Atividade Moderada", "Muito Ativo",
				"Extremamente Ativo");
		sex.setPromptText("Sex");
		activityLevel.setPromptText("Activity level");
		agePicker.setPromptText("Date of birth (YYYY-MM-dd)");
	}

	@FXML
	protected void goBack() {
		LoginApp.getInstance().gotoLogin();
	}

	@FXML
	protected void processAddUser() {
		fadeMessages();
		try {
			checkMessages();
			try {
				UserServices.getUserByEmail(email.getText());
				emailErrorMessage.setText("Email already in use");
				animateMessage(emailErrorMessage);
			} catch (Exception e2) {
				try {
					LocalDate birth = new LocalDate(agePicker.getValue().getYear(),agePicker.getValue().getMonthValue(),agePicker.getValue().getDayOfMonth());
					LocalDate now = new LocalDate();
					Years agejoda = Years.yearsBetween(birth, now);
					String data = String.valueOf(agejoda.getYears());
					UserServices.addUser(name.getText(), email.getText(), password.getText(), data,
							activityLevel.getSelectionModel().getSelectedItem(),
							sex.getSelectionModel().getSelectedItem(), height.getText(), weight.getText());
					successMessage.setText("User successfully added!");
					animateMessage(successMessage);
				} catch (Exception e) {
					errorMessage.setText("Error adding user!");
					animateMessage(errorMessage);
				}
			}
		} catch (Exception e1) {
			errorMessage.setText("Please review inserted values");
			animateMessage(errorMessage);
		}
	}

	private void animateMessage(Label message) {
		FadeTransition ft = new FadeTransition(new Duration(1000), message);
		ft.setFromValue(0.0);
		ft.setToValue(1);
		ft.play();
	}

	private void fadeMessages() {
		emailErrorMessage.setText("");
		nameErrorMessage.setText("");
		passwordErrorMessage.setText("");
		password2ErrorMessage.setText("");
		successMessage.setText("");
		errorMessage.setText("");
	}

	private void checkMessages() throws Exception {

		// TODO falta regex para age, weight e height e verificações para os 3

		boolean flag = false;
		if (email.getText().equals("")) {
			emailErrorMessage.setText("Email cannot be empty");
			animateMessage(emailErrorMessage);
			flag = true;
		}
		if (name.getText().equals("")) {
			nameErrorMessage.setText("Name cannot be empty");
			animateMessage(emailErrorMessage);
			flag = true;
		}
		if (password.getText().equals("")) {
			passwordErrorMessage.setText("Password cannot be empty");
			animateMessage(passwordErrorMessage);
			flag = true;
		}
		if (!password.getText().equals("") && !password2.getText().equals("")
				&& !password.getText().equals(password2.getText())) {
			password2ErrorMessage.setText("Passwords do not match");
			animateMessage(password2ErrorMessage);
			flag = true;
		}
		if (password2.getText().equals("")) {
			password2ErrorMessage.setText("Password must be rewritten");
			animateMessage(password2ErrorMessage);
			flag = true;
		}
		if (!email.getText().equals("") && !emailRegex.matcher(email.getText()).find()) {
			emailErrorMessage.setText("Email is malformed");
			animateMessage(emailErrorMessage);
			flag = true;
		}
		if (flag) {
			throw new Exception();
		}
	}

}
