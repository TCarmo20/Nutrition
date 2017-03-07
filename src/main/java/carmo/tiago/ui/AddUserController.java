package carmo.tiago.ui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import org.joda.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import carmo.tiago.services.UserServices;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class AddUserController implements Initializable {

	@FXML
	private BorderPane AddUser;

	@FXML
	private JFXTextField name;

	@FXML
	private Label nameErrorMessage;

	@FXML
	private JFXTextField email;

	@FXML
	private Label emailErrorMessage;

	@FXML
	private JFXPasswordField password;

	@FXML
	private Label passwordErrorMessage;

	@FXML
	private JFXPasswordField password2;

	@FXML
	private Label password2ErrorMessage;

	@FXML
	private JFXDatePicker agePicker;

	@FXML
	private Label dateErrorMessage;

	@FXML
	private JFXTextField weight;

	@FXML
	private JFXTextField height;

	@FXML
	private Label weightHeightErrorMessage;

	@FXML
	private JFXComboBox<String> sex;

	@FXML
	private JFXComboBox<String> activityLevel;

	@FXML
	private Label sexActivityErrorMessage;

	@FXML
	private Label errorMessage;

	@FXML
	private JFXButton confirmAddUser;

	@FXML
	private JFXButton backAddUser;

	private Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		name.setPromptText("Name");
		email.setPromptText("Email");
		password.setPromptText("Password");
		password2.setPromptText("Retype password");
		height.setPromptText("Height (cm)");
		weight.setPromptText("Weight (kg)");
		errorMessage.setOpacity(0);
		sex.getItems().addAll("Male", "Female");
		activityLevel.getItems().addAll("Sedentary", "Light activity", "Moderate activity", "Very active",
				"Extremely Active");
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
					UserServices.addUser(name.getText(), email.getText(), password.getText(), agePicker.getValue().toString(),
							activityLevel.getSelectionModel().getSelectedItem(),
							sex.getSelectionModel().getSelectedItem(), height.getText(), weight.getText());
					LoginApp.getInstance().gotoLogin();
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
		errorMessage.setText("");
		weightHeightErrorMessage.setText("");
		sexActivityErrorMessage.setText("");
		dateErrorMessage.setText("");
	}

	private void checkMessages() throws Exception {
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
		if (sex.getSelectionModel().getSelectedItem() == null
				&& !(activityLevel.getSelectionModel().getSelectedItem() == null)) {
			sexActivityErrorMessage.setText("Please select a gender");
			animateMessage(sexActivityErrorMessage);
			flag = true;
		} else if (sex.getSelectionModel().getSelectedItem() == null
				&& activityLevel.getSelectionModel().getSelectedItem() == null) {
			sexActivityErrorMessage.setText("Please select a gender and activity level");
			animateMessage(sexActivityErrorMessage);
			flag = true;
		} else if (!(sex.getSelectionModel().getSelectedItem() == null)
				&& activityLevel.getSelectionModel().getSelectedItem() == null) {
			sexActivityErrorMessage.setText("Please select an activity level");
			animateMessage(sexActivityErrorMessage);
			flag = true;
		}
		if (height.getText().equals("") && !weight.getText().equals("")) {
			weightHeightErrorMessage.setText("Height cannot be empty");
			animateMessage(weightHeightErrorMessage);
			flag = true;
		} else if (!height.getText().equals("") && weight.getText().equals("")) {
			weightHeightErrorMessage.setText("Weight cannot be empty");
			animateMessage(weightHeightErrorMessage);
			flag = true;
		} else if (height.getText().equals("") && weight.getText().equals("")) {
			weightHeightErrorMessage.setText("Height and weight cannot be empty");
			animateMessage(weightHeightErrorMessage);
			flag = true;
		}
		if (agePicker.getValue() == null) {
			dateErrorMessage.setText("Please insert a date");
			animateMessage(dateErrorMessage);
			flag = true;
		} else if (agePicker.getValue().getYear() == new LocalDate().getYear()
				|| agePicker.getValue().getYear() >= new LocalDate().getYear()) {
			dateErrorMessage.setText("Please insert a valid date");
			animateMessage(dateErrorMessage);
			flag = true;
		}
		if (flag) {
			throw new Exception();
		}
	}

}
