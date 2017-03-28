package carmo.tiago.ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import carmo.tiago.services.UserServices;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class UpdateDetailsController implements Initializable {

	@FXML
	private AnchorPane HomePage;

	@FXML
	private StackPane stackPane;

	@FXML
	private JFXTextField updateName;

	@FXML
	private Label updateNameLabel;

	@FXML
	private JFXTextField updateEmail;

	@FXML
	private Label updateEmailLabel;

	@FXML
	private JFXPasswordField updatePassword;

	@FXML
	private Label updatePasswordLabel;

	@FXML
	private JFXPasswordField updatePassword2;

	@FXML
	private Label updatePassword2Label;

	@FXML
	private JFXDatePicker updateAge;

	@FXML
	private Label updateAgeLabel;

	@FXML
	private JFXTextField updateWeight;

	@FXML
	private JFXTextField updateHeight;

	@FXML
	private Label updateHeightWeightLabel;

	@FXML
	private JFXComboBox<String> updateSex;

	@FXML
	private JFXComboBox<String> updateActivity;

	@FXML
	private Label updateSexActivityLevel;

	@FXML
	private JFXButton updateUserBtn;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;

	private HamburgerBasicCloseTransition burgerTask;

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateDetailsController.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeHomePage();
		updateName.setText(LoginApp.getInstance().getLoggedUser().getName());
		updateEmail.setText(LoginApp.getInstance().getLoggedUser().getEmail());
		updateHeight.setText(LoginApp.getInstance().getLoggedUser().getHeight());
		updateWeight.setText(LoginApp.getInstance().getLoggedUser().getWeight());
		updateSex.getItems().addAll(Sex.MALE.getSexString(), Sex.FEMALE.getSexString());
		updateActivity.getItems().addAll(ActivityLevel.SEDENTARY.getActivityString(),
				ActivityLevel.LIGHT.getActivityString(), ActivityLevel.MODERATE.getActivityString(),
				ActivityLevel.VERY.getActivityString(), ActivityLevel.EXTREMELY.getActivityString());
		updateSex.setValue(LoginApp.getInstance().getLoggedUser().getSex());
		updateActivity.setValue(LoginApp.getInstance().getLoggedUser().getActivityLevel());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(LoginApp.getInstance().getLoggedUser().getDob(), formatter);
		updateAge.setValue(localDate);
		LOGGER.info("Changed to user update screen");
	}

	public void initializeHomePage() {
		try {
			AnchorPane anchor = FXMLLoader.load(getClass().getResource("/DrawerContent.fxml"));
			drawer.setSidePane(anchor);
			burgerTask = new HamburgerBasicCloseTransition(hamburger);
			burgerTask.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
				burgerTask.setRate(burgerTask.getRate() * -1);
				burgerTask.play();
				if (drawer.isShown()) {
					drawer.close();
				} else {
					drawer.open();
				}
			});
		} catch (IOException e) {
			LOGGER.error("Error initializing Home Page's drawer: " + e);
		}
		LOGGER.info("Home Page initialized");
	}

	@FXML
	private void processUpdateUser() {
		fadeMessages();
		try {
			checkMessages();
			UserServices.updateUser(updateName.getText(), updateEmail.getText(), updatePassword.getText(),
					updateAge.getValue().toString(), updateActivity.getSelectionModel().getSelectedItem(),
					updateSex.getSelectionModel().getSelectedItem(), updateHeight.getText(), updateWeight.getText());
			JFXDialogLayout content = new JFXDialogLayout();
			content.setHeading(new Text("User Successfully Updated!"));
			JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
			JFXButton create = new JFXButton("OK");
			create.setButtonType(ButtonType.RAISED);
			create.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						dialog.close();
						DrawerContentController.getInstance().nameLabel.setText("Welcome, " + updateName.getText());
					} catch (Exception e) {
						LOGGER.error("Error closing dialog: " + e);
					}
				}
			});
			content.setActions(create);
			dialog.toFront();
			dialog.show();
		} catch (Exception e) {
			LOGGER.error("Error updating user: " + e);
		}
	}

	private void fadeMessages() {
		updateEmailLabel.setText("");
		updateNameLabel.setText("");
		updatePasswordLabel.setText("");
		updatePassword2Label.setText("");
		updateHeightWeightLabel.setText("");
		updateSexActivityLevel.setText("");
		updateAgeLabel.setText("");
		LOGGER.info("Messages cleared");
	}

	private void checkMessages() throws Exception {
		Calendar date = new GregorianCalendar();
		Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		boolean flag = false;
		if (updateEmail.getText().equals("")) {
			updateEmailLabel.setText("Email cannot be empty");
			animateMessage(updateEmailLabel);
			flag = true;
		}
		if (updateName.getText().equals("")) {
			updateNameLabel.setText("Name cannot be empty");
			animateMessage(updateNameLabel);
			flag = true;
		}
		if (updatePassword.getText().equals("")) {
			updatePasswordLabel.setText("Password cannot be empty");
			animateMessage(updatePasswordLabel);
			flag = true;
		}
		if (!updatePassword.getText().equals("") && !updatePassword2.getText().equals("")
				&& !updatePassword.getText().equals(updatePassword2.getText())) {
			updatePassword2Label.setText("Passwords do not match");
			animateMessage(updatePassword2Label);
			flag = true;
		}
		if (updatePassword2.getText().equals("")) {
			updatePassword2Label.setText("Password must be rewritten");
			animateMessage(updatePassword2Label);
			flag = true;
		}
		if (!updateEmail.getText().equals("") && !emailRegex.matcher(updateEmail.getText()).find()) {
			updateEmailLabel.setText("Email is malformed");
			animateMessage(updateEmailLabel);
			flag = true;
		}
		if (updateSex.getSelectionModel().getSelectedItem() == null
				&& !(updateActivity.getSelectionModel().getSelectedItem() == null)) {
			updateSexActivityLevel.setText("Please select a gender");
			animateMessage(updateSexActivityLevel);
			flag = true;
		} else if (updateSex.getSelectionModel().getSelectedItem() == null
				&& updateActivity.getSelectionModel().getSelectedItem() == null) {
			updateSexActivityLevel.setText("Please select a gender and activity level");
			animateMessage(updateSexActivityLevel);
			flag = true;
		} else if (!(updateSex.getSelectionModel().getSelectedItem() == null)
				&& updateActivity.getSelectionModel().getSelectedItem() == null) {
			updateSexActivityLevel.setText("Please select an activity level");
			animateMessage(updateSexActivityLevel);
			flag = true;
		}
		if (updateHeight.getText().equals("") && !updateWeight.getText().equals("")) {
			updateHeightWeightLabel.setText("Height cannot be empty");
			animateMessage(updateHeightWeightLabel);
			flag = true;
		} else if (!updateHeight.getText().equals("") && updateWeight.getText().equals("")) {
			updateHeightWeightLabel.setText("Weight cannot be empty");
			animateMessage(updateHeightWeightLabel);
			flag = true;
		} else if (updateHeight.getText().equals("") && updateWeight.getText().equals("")) {
			updateHeightWeightLabel.setText("Height and weight cannot be empty");
			animateMessage(updateHeightWeightLabel);
			flag = true;
		}
		if (updateAge.getValue() == null) {
			updateAgeLabel.setText("Please insert a date");
			animateMessage(updateAgeLabel);
			flag = true;
		} else if (updateAge.getValue().getYear() == date.get(Calendar.YEAR)
				|| updateAge.getValue().getYear() >= date.get(Calendar.YEAR)) {
			updateAgeLabel.setText("Please insert a valid date");
			animateMessage(updateAgeLabel);
			flag = true;
		}
		if (flag) {
			LOGGER.error("User update details not properly filled");
			throw new Exception();
		}
	}

	private void animateMessage(Label message) {
		FadeTransition ft = new FadeTransition(new Duration(1000), message);
		ft.setFromValue(0.0);
		ft.setToValue(1);
		ft.play();
	}

}
