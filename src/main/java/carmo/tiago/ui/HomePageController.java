package carmo.tiago.ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import java.util.List;
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
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import carmo.tiago.services.NutPlanPOJO;
import carmo.tiago.services.UserServices;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
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
	private JFXTreeTableView<NutPlanPOJO> myPlansTable;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;

	@FXML
	private JFXTextField nameField;

	@FXML
	private JFXComboBox<String> objectives;

	@FXML
	private JFXButton create;

	@FXML
	private Label errorLabelOb;

	@FXML
	private Label errorLabelName;

	@FXML
	private VBox vBoxPlans;

	@FXML
	private JFXTextField caloriesPlan;

	@FXML
	private JFXTextField proteinPlan;

	@FXML
	private JFXTextField carbsPlan;

	@FXML
	private JFXTextField fatPlan;

	@FXML
	private JFXTextField filter;

	@FXML
	private JFXButton exportPlanBtn;

	@FXML
	private JFXButton deletePlanBtn;

	@FXML
	private Label deleteExportLabel;
	
	private JFXDialog dialog;

	private HamburgerBasicCloseTransition burgerTask;

	private static NutPlanPOJO rowData;

	private static final Logger LOGGER = LoggerFactory.getLogger(HomePageController.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		myPlansGroup.setOpacity(0);
		updateUserGroup.setOpacity(0);
		try {
			AnchorPane anchor = FXMLLoader.load(getClass().getResource("/DrawerContent.fxml"));
			drawer.setSidePane(anchor);
			VBox vbox = (VBox) anchor.getChildren().get(0);
			for (Node node : vbox.getChildren()) {
				if (node.getAccessibleText() != null) {
					node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
						switch (node.getAccessibleText()) {
						case "Create Plan":
							fadeMessages();
							processCreatePlan();
							break;
						case "My Plans":
							fadeMessages();
							processMyPlans();
							break;
						case "Update Details":
							fadeMessages();
							processUpdateUserScreen();
							break;
						case "Logout":
							fadeMessages();
							processLogout();
							break;
						}
					});
				}
			}
			burgerTask = new HamburgerBasicCloseTransition(hamburger);
			burgerTask.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
				burgerTask.setRate(burgerTask.getRate() * -1);
				burgerTask.play();
				if (drawer.isShown()) {
					drawer.close();
				} else {
					drawer.open();
					dialog.close();
				}
			});
			loadDialog();
		} catch (IOException e) {
			LOGGER.error("Error initializing Home Page's drawer: " + e);
		}
		LOGGER.info("Home Page initialized");
	}

	private void loadDialog() {
		JFXDialogLayout content = new JFXDialogLayout();
		dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
		content.setHeading(new Text("Welcome to the Nutrition app"));
		content.setBody(new Text(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec lorem risus, aliquam quis vehicula vitae,"
						+ "\nrutrum non elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer commodo sodales purus,"
						+ "\nnec dignissim odio laoreet eu. Ut viverra lorem vitae mauris rutrum, ac elementum mauris dapibus."
						+ "\nFusce ut sollicitudin justo. Nunc ac posuere erat. Cras eget lorem elit. Aenean ultrices molestie"
						+ "\nrisus ut consequat. Aliquam ultricies magna vitae quam posuere pellentesque.\n\nMorbi scelerisque pretium maximus."
						+ "\nNunc maximus urna in rhoncus porttitor. Sed vestibulum mollis vestibulum. Suspendisse placerat blandit"
						+ "\nmauris sed pellentesque. Nulla tempor augue consectetur, aliquet ipsum vitae, pharetra ligula."
						+ "\nVivamus commodo elementum scelerisque. Vivamus ac leo sed diam molestie aliquet quis id erat."
						+ "\nInteger hendrerit, felis sit amet efficitur iaculis, metus nisl interdum risus, in suscipit massa odio vitae enim."
						+ "\nMaecenas at odio vulputate, ullamcorper eros nec, ultrices diam. Aliquam at vehicula orci."
						+ "\nVestibulum rhoncus consequat velit eget dapibus."));
		JFXButton close = new JFXButton("OK");
		close.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});
		content.setActions(close);
		dialog.show();
	}

	private void processMyPlans() {
		drawer.close();
		burgerTask.setRate(-1);
		burgerTask.play();
		myPlansTable.getColumns().clear();
		updateTable();
		myPlansGroup.setOpacity(1);
		updateUserGroup.setOpacity(0);
		myPlansGroup.toFront();
		updateUserGroup.toBack();
	}

	@SuppressWarnings("unchecked")
	private void updateTable() {
		List<NutPlanPOJO> planList;
		try {
			planList = UserServices.getUserPlans(LoginApp.getInstance().getLoggedUser().getUserId());
			JFXTreeTableColumn<NutPlanPOJO, String> nameCol = new JFXTreeTableColumn<>("Name");
			nameCol.setCellValueFactory(
					new Callback<TreeTableColumn.CellDataFeatures<NutPlanPOJO, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(
								TreeTableColumn.CellDataFeatures<NutPlanPOJO, String> param) {
							return new SimpleStringProperty(param.getValue().getValue().getName());
						}
					});
			nameCol.prefWidthProperty().bind(myPlansTable.widthProperty().divide(2));
			JFXTreeTableColumn<NutPlanPOJO, String> objectiveCol = new JFXTreeTableColumn<>("Objective");
			objectiveCol.setCellValueFactory(
					new Callback<TreeTableColumn.CellDataFeatures<NutPlanPOJO, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(
								TreeTableColumn.CellDataFeatures<NutPlanPOJO, String> param) {
							return new SimpleStringProperty(param.getValue().getValue().getObjective());
						}
					});
			objectiveCol.prefWidthProperty().bind(myPlansTable.widthProperty().divide(2));
			ObservableList<NutPlanPOJO> plans = FXCollections.observableArrayList(planList);
			TreeItem<NutPlanPOJO> root = new RecursiveTreeItem<NutPlanPOJO>(plans, RecursiveTreeObject::getChildren);
			myPlansTable.setRoot(root);
			myPlansTable.setShowRoot(false);
			myPlansTable.getColumns().setAll(nameCol, objectiveCol);
			myPlansTable.setRowFactory(tv -> {
				JFXTreeTableRow<NutPlanPOJO> row = new JFXTreeTableRow<>();
				row.setOnMouseClicked(event -> {
					fadeMessages();
					rowData = row.getItem();
					if (rowData != null) {
						caloriesPlan.setText(String.valueOf(rowData.getCalories()));
						proteinPlan.setText(String.valueOf(rowData.getProtein()));
						carbsPlan.setText(String.valueOf(rowData.getCarbs()));
						fatPlan.setText(String.valueOf(rowData.getFat()));
					}
				});
				return row;
			});
			filter.textProperty().addListener((o, oldVal, newVal) -> {
				myPlansTable.setPredicate(plan -> plan.getValue().getName().contains(newVal)
						|| plan.getValue().getObjective().contains(newVal));
			});
		} catch (Exception e) {
			LOGGER.error("Error building tableview: " + e);
		}
	}

	private void processUpdateUserScreen() {
		drawer.close();
		burgerTask.setRate(-1);
		burgerTask.play();
		myPlansGroup.setOpacity(0);
		updateUserGroup.setOpacity(1);
		myPlansGroup.toBack();
		updateUserGroup.toFront();
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

	private void processCreatePlan() {
		drawer.close();
		burgerTask.setRate(-1);
		burgerTask.play();
		JFXDialogLayout content = new JFXDialogLayout();
		JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
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
					processMyPlans();
				} catch (Exception e) {
					JFXDialogLayout content2 = new JFXDialogLayout();
					content2.setHeading(new Text("Error creating plan!"));
					JFXDialog dialog2 = new JFXDialog(stackPane, content2, JFXDialog.DialogTransition.CENTER);
					JFXButton create2 = new JFXButton("OK");
					create2.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event2) {
							dialog2.close();
						}
					});
					content2.setActions(create2);
					dialog2.show();
					LOGGER.error("Error creating plan: " + e);
				}
			}
		});
		VBox mainVbox = new VBox(20);
		HBox nameHbox = new HBox(10);
		nameHbox.getChildren().addAll(nameField, errorLabelName);
		HBox obHbox = new HBox(10);
		obHbox.getChildren().addAll(objectives, errorLabelOb);
		mainVbox.getChildren().addAll(nameHbox, obHbox);
		content.setBody(mainVbox);
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
		deleteExportLabel.setText("");
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

	@FXML
	private void processExportPlan(ActionEvent event) {
		fadeMessages();
		if (rowData != null) {
			JFXDialogLayout content = new JFXDialogLayout();
			JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
			content.setHeading(new Text("Plan name: \n\n"));
			JFXTextField name = new JFXTextField();
			name.setPromptText("Name");
			name.setPrefWidth(150);
			Label errorLabel = new Label();
			errorLabel.setPrefWidth(150);
			JFXButton export = new JFXButton("EXPORT");
			export.setButtonType(ButtonType.RAISED);
			export.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (!name.getText().equals("")) {
						try {
							CreatePDF.createPDF(rowData, name.getText());
							dialog.close();
							JFXDialogLayout content2 = new JFXDialogLayout();
							JFXDialog dialog2 = new JFXDialog(stackPane, content2, JFXDialog.DialogTransition.CENTER);
							content2.setHeading(new Text("Plan successfully exported"));
							JFXButton exportOk = new JFXButton("OK");
							exportOk.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									dialog2.close();
								}
							});
							content2.setActions(exportOk);
							dialog2.show();
						} catch (Exception e) {
							LOGGER.error("Error exporting to pdf: " + e);
							JFXDialogLayout content2 = new JFXDialogLayout();
							JFXDialog dialog2 = new JFXDialog(stackPane, content2, JFXDialog.DialogTransition.CENTER);
							content2.setHeading(new Text("Problem exporting plan"));
							JFXButton exportOk = new JFXButton("OK");
							exportOk.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									dialog2.close();
								}
							});
							content2.setActions(exportOk);
							dialog2.show();
						}
					} else {
						errorLabel.setTextFill(Color.RED);
						DropShadow ds = new DropShadow();
						ds.setOffsetY(3.0f);
						errorLabel.setEffect(ds);
						errorLabel.setText("Please enter a name");
						animateMessage(errorLabel);
					}
				}
			});
			HBox hbox = new HBox(10);
			hbox.getChildren().addAll(name, errorLabel);
			content.setBody(hbox);
			content.setActions(export);
			dialog.show();
		} else {
			deleteExportLabel.setText("Please select a plan first");
			animateMessage(updateAgeLabel);
		}
	}

	@FXML
	private void processDeletePlan(ActionEvent event) {
		fadeMessages();
		if (rowData != null) {
			JFXDialogLayout content1 = new JFXDialogLayout();
			content1.setHeading(new Text("Are you sure?"));
			JFXDialog dialog1 = new JFXDialog(stackPane, content1, JFXDialog.DialogTransition.CENTER);
			JFXButton delete = new JFXButton("YES");
			delete.setButtonType(ButtonType.RAISED);
			delete.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						dialog1.close();
						UserServices.deletePlan(rowData);
						updateTable();
						rowData = null;
						caloriesPlan.setText("");
						proteinPlan.setText("");
						carbsPlan.setText("");
						fatPlan.setText("");
					} catch (Exception e1) {
						JFXDialogLayout content3 = new JFXDialogLayout();
						content3.setHeading(new Text("Error deleting plan!"));
						JFXDialog dialog3 = new JFXDialog(stackPane, content3, JFXDialog.DialogTransition.CENTER);
						JFXButton create3 = new JFXButton("OK");
						create3.setButtonType(ButtonType.RAISED);
						create3.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								try {
									dialog3.close();
								} catch (Exception e) {
									LOGGER.error("Error closing dialog: " + e);
								}
							}
						});
						content3.setActions(create3);
						dialog3.toFront();
						dialog3.show();
					}
				}
			});
			JFXButton noDelete = new JFXButton("NO");
			noDelete.setButtonType(ButtonType.RAISED);
			noDelete.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					dialog1.close();
				}
			});
			HBox hbox = new HBox(10);
			hbox.getChildren().addAll(delete, noDelete);
			content1.setActions(hbox);
			dialog1.toFront();
			dialog1.show();
		} else {
			deleteExportLabel.setText("Please select a plan first");
			animateMessage(updateAgeLabel);
		}
	}

}
