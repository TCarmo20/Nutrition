package carmo.tiago.ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import carmo.tiago.services.NutPlanPOJO;
import carmo.tiago.services.UserServices;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Tiago Carmo
 *
 */
public class HomePageController implements Initializable {

	@FXML
	private AnchorPane HomePage;

	@FXML
	private StackPane stackPane;

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

	@FXML
	private Group myPlansGroup;

	@FXML
	private TableView<NutPlanPOJO> myPlansTable;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;

	@FXML
	private Label errorLabelOb;

	@FXML
	private Label errorLabelName;

	@FXML
	private JFXTextField nameField;

	@FXML
	private JFXComboBox<String> objectives;

	@FXML
	private JFXButton create;

	private List<NutPlanPOJO> planList;

	private Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	private static final Logger LOGGER = LoggerFactory.getLogger(HomePageController.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		drawer.open();
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
			hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
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

	@SuppressWarnings("unchecked")
	private void processMyPlans() {
		drawer.close();
		myPlansTable.getColumns().clear();
		try {
			planList = UserServices.getUserPlans(LoginApp.getInstance().getLoggedUser().getUserId());
			ObservableList<NutPlanPOJO> plans = FXCollections.observableArrayList(planList);

			TableColumn<NutPlanPOJO, String> nameCol = new TableColumn<NutPlanPOJO, String>("Name");
			nameCol.setCellValueFactory(new PropertyValueFactory<NutPlanPOJO, String>("name"));
			nameCol.prefWidthProperty().bind(myPlansTable.widthProperty().divide(6));

			TableColumn<NutPlanPOJO, String> objectiveCol = new TableColumn<NutPlanPOJO, String>("Objective");
			objectiveCol.setCellValueFactory(new PropertyValueFactory<NutPlanPOJO, String>("objective"));
			objectiveCol.prefWidthProperty().bind(myPlansTable.widthProperty().divide(6));

			TableColumn<NutPlanPOJO, Double> caloriesCol = new TableColumn<NutPlanPOJO, Double>("Calories");
			caloriesCol.setCellValueFactory(new PropertyValueFactory<NutPlanPOJO, Double>("calories"));
			caloriesCol.prefWidthProperty().bind(myPlansTable.widthProperty().divide(6));

			TableColumn<NutPlanPOJO, Double> proteinCol = new TableColumn<NutPlanPOJO, Double>("Protein");
			proteinCol.setCellValueFactory(new PropertyValueFactory<NutPlanPOJO, Double>("protein"));
			proteinCol.prefWidthProperty().bind(myPlansTable.widthProperty().divide(6));

			TableColumn<NutPlanPOJO, Double> carbsCol = new TableColumn<NutPlanPOJO, Double>("Carbs");
			carbsCol.setCellValueFactory(new PropertyValueFactory<NutPlanPOJO, Double>("carbs"));
			carbsCol.prefWidthProperty().bind(myPlansTable.widthProperty().divide(6));

			TableColumn<NutPlanPOJO, Double> fatCol = new TableColumn<NutPlanPOJO, Double>("Fat");
			fatCol.setCellValueFactory(new PropertyValueFactory<NutPlanPOJO, Double>("fat"));
			fatCol.prefWidthProperty().bind(myPlansTable.widthProperty().divide(6));

			myPlansTable.getColumns().addAll(nameCol, objectiveCol, caloriesCol, proteinCol, carbsCol, fatCol);
			myPlansTable.setItems(plans);
		} catch (Exception e) {
			LOGGER.error("Error building tableview: " + e);
		}
		myPlansGroup.setOpacity(1);
		updateUserGroup.setOpacity(0);
		myPlansGroup.toFront();
		updateUserGroup.toBack();
	}

	private void processUpdateUserScreen() {
		drawer.close();
		myPlansGroup.setOpacity(0);
		updateUserGroup.setOpacity(1);
		myPlansGroup.toBack();
		updateUserGroup.toFront();
		LOGGER.info("Changed to user update screen");
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

	protected void processLogout() {
		LoginApp.getInstance().setLoggedUser(null);
		LoginController.setUser(null);
		LoginApp.getInstance().gotoLogin();
	}

	public void processCreatePlan() {
		drawer.close();
		JFXDialogLayout content = new JFXDialogLayout();
		JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
		FlowPane flow = new FlowPane(Orientation.VERTICAL);
		flow.setRowValignment(VPos.CENTER);
		flow.setColumnHalignment(HPos.CENTER);
		flow.setPrefWrapLength(200);
		flow.setVgap(20);
		flow.setHgap(10);
		flow.setPrefWidth(200);

		content.setHeading(new Text("Choose your plan details: \n\n"));

		errorLabelOb = new Label();
		errorLabelOb.setPrefWidth(150);
		objectives = new JFXComboBox<String>();
		objectives.getItems().addAll("Hypertrophy", "Maintenance", "Fat Loss");
		objectives.setPromptText("Objective");
		objectives.setPrefWidth(150);

		errorLabelName = new Label();
		errorLabelName.setPrefWidth(150);
		nameField = new JFXTextField();
		nameField.setPromptText("Name");
		nameField.setPrefWidth(150);

		create = new JFXButton("CREATE");
		create.setButtonType(ButtonType.RAISED);
		create.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				fadeMessagesPlan();
				try {
					checkMessagesPlan();
					UserServices.createPlan(nameField.getText(), objectives.getSelectionModel().getSelectedItem());
					dialog.close();
					JFXDialogLayout content2 = new JFXDialogLayout();
					content2.setHeading(new Text("Plan created!"));
					JFXDialog dialog2 = new JFXDialog(stackPane, content2, JFXDialog.DialogTransition.CENTER);
					JFXButton create2 = new JFXButton("OK");
					create2.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event2) {
							dialog2.close();
							processMyPlans();
						}
					});
					content2.setActions(create2);
					dialog2.show();
				} catch (Exception e) {
					LOGGER.error("Error creating plan: " + e);
				}
			}
		});
		flow.getChildren().addAll(nameField, errorLabelName, objectives, errorLabelOb, create);
		content.setBody(flow);
		content.setActions(create);
		dialog.show();
	}

	private void fadeMessagesPlan() {
		errorLabelName.setText("");
		errorLabelOb.setText("");
		LOGGER.info("Messages cleared");
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

	private void checkMessagesPlan() throws Exception {
		boolean flag = false;
		if (objectives.getSelectionModel().isEmpty()) {
			errorLabelOb.setTextFill(Color.RED);
			DropShadow ds = new DropShadow();
			ds.setOffsetY(3.0f);
			errorLabelOb.setEffect(ds);
			errorLabelOb.setText("Please select an objective");
			animateMessage(errorLabelOb);
			flag = true;
		}
		if (nameField.getText().equals("")) {
			errorLabelName.setTextFill(Color.RED);
			DropShadow ds = new DropShadow();
			ds.setOffsetY(3.0f);
			errorLabelName.setEffect(ds);
			errorLabelName.setText("Please add a name");
			animateMessage(errorLabelName);
			flag = true;
		}
		if (flag) {
			LOGGER.error("Plan creation fields not properly filled");
			throw new Exception();
		}

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
