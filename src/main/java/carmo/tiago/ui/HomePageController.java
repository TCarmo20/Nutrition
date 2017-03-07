package carmo.tiago.ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import carmo.tiago.services.UserServices;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Java FX FXML Controller.
 * 
 * @author Tarun Tyagi
 */
public class HomePageController implements Initializable {

	@FXML
	private StackPane HomePage;

	@FXML
	private Group myPlansGroup;

	@FXML
	private JFXTreeTableView<?> myPlansTable;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;

	@FXML
	private Group updateUserGroup;

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

	private HamburgerBackArrowBasicTransition hamTran;

	private Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		drawer.open();
		anchorPane.toFront();
		myPlansGroup.setOpacity(0);
		updateUserGroup.setOpacity(0);
		updateName.setText(LoginApp.getInstance().getLoggedUser().getName());
		updateEmail.setText(LoginApp.getInstance().getLoggedUser().getEmail());
		updateHeight.setText(LoginApp.getInstance().getLoggedUser().getHeight());
		updateWeight.setText(LoginApp.getInstance().getLoggedUser().getWeight());
		updateSex.getItems().addAll("Male", "Female");
		updateActivity.getItems().addAll("Sedentary", "Light activity", "Moderate activity", "Very active",
				"Extremely Active");
		updateSex.setValue(LoginApp.getInstance().getLoggedUser().getSex());
		updateActivity.setValue(LoginApp.getInstance().getLoggedUser().getActivityLevel());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(LoginApp.getInstance().getLoggedUser().getDob(), formatter);
		updateAge.setValue(localDate);
		try {
			AnchorPane anchor = FXMLLoader.load(getClass().getResource("/DrawerContent.fxml"));
			drawer.setSidePane(anchor);
			drawer.setResizableOnDrag(false);
			VBox vbox = (VBox) anchor.getChildren().get(0);
			for (Node node : vbox.getChildren()) {
				if (node.getAccessibleText() != null) {
					node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
						switch (node.getAccessibleText()) {
						case "Create Plan":
							processCreatePlan();
							break;
						case "My Plans":
							processMyPlans();
							break;
						case "Update Details":
							processUpdateUserScreen();
							break;
						case "Logout":
							processLogout();
							break;
						}
					});
				}
			}
			hamTran = new HamburgerBackArrowBasicTransition(hamburger);
			hamTran.setRate(-1);
			hamTran.play();
			hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
				hamTran.setRate(hamTran.getRate() * -1);
				hamTran.play();
				if (drawer.isShown()) {
					anchorPane.toBack();
					drawer.close();
				} else {
					anchorPane.toFront();
					drawer.open();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processMyPlans() {
		hamTran.setRate(hamTran.getRate() * -1);
		hamTran.play();
		drawer.close();
		myPlansGroup.toFront();
		myPlansGroup.setOpacity(1);
		updateUserGroup.setOpacity(0);
	}

	private void processUpdateUserScreen() {
		hamTran.setRate(hamTran.getRate() * -1);
		hamTran.play();
		drawer.close();
		updateUserGroup.toFront();
		myPlansGroup.setOpacity(0);
		updateUserGroup.setOpacity(1);
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
			JFXDialog dialog = new JFXDialog(HomePage, content, JFXDialog.DialogTransition.CENTER);
			JFXButton create = new JFXButton("Ok");
			create.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						dialog.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
			content.setActions(create);
			dialog.show();
		} catch (Exception e) {

		}
	}

	protected void processLogout() {
		LoginApp.getInstance().setLoggedUser(null);
		LoginApp.getInstance().gotoLogin();
	}

	public void processCreatePlan() {
		JFXDialogLayout content = new JFXDialogLayout();
		content.setHeading(new Text("Choose your objective"));
		JFXComboBox<String> objectives = new JFXComboBox<String>();
		objectives.getItems().addAll("Hypertrophy", "Maintenance", "Fat Loss");
		content.setBody(objectives);
		JFXDialog dialog = new JFXDialog(HomePage, content, JFXDialog.DialogTransition.CENTER);
		JFXButton create = new JFXButton("Create");
		create.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					UserServices.createPlan(objectives.getSelectionModel().getSelectedItem());
					dialog.close();
					JFXDialogLayout content2 = new JFXDialogLayout();
					content2.setHeading(new Text("Plan created!"));
					JFXDialog dialog2 = new JFXDialog(HomePage, content2, JFXDialog.DialogTransition.CENTER);
					JFXButton create2 = new JFXButton("Ok");
					create2.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event2) {
							dialog2.close();
						}
					});
					content2.setActions(create2);
					dialog2.show();
				} catch (Exception e) {
					JFXDialogLayout content3 = new JFXDialogLayout();
					content3.setHeading(new Text("Error creating plan! Exception: " + e));
					JFXDialog dialog3 = new JFXDialog(HomePage, content3, JFXDialog.DialogTransition.CENTER);
					JFXButton create3 = new JFXButton("Ok");
					create.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event3) {
							dialog3.close();
						}
					});
					content3.setActions(create3);
					dialog3.show();
				}

			}
		});
		content.setActions(create);
		dialog.show();
	}

	private void fadeMessages() {
		updateEmailLabel.setText("");
		updateNameLabel.setText("");
		updatePasswordLabel.setText("");
		updatePassword2Label.setText("");
		updateHeightWeightLabel.setText("");
		updateSexActivityLevel.setText("");
		updateAgeLabel.setText("");
	}

	private void checkMessages() throws Exception {
		Calendar date = new GregorianCalendar();
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
