package carmo.tiago.ui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import carmo.tiago.services.MealPOJO;
import carmo.tiago.services.MealServices;
import carmo.tiago.services.NutPlanPOJO;
import carmo.tiago.services.NutPlanServices;
import carmo.tiago.services.UserPOJO;
import carmo.tiago.services.UserServices;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;

public class AdminPageController implements Initializable {

	@FXML
	private AnchorPane HomePage;

	@FXML
	private StackPane stackPane;

	@FXML
	private Group AdminGroup;

	@FXML
	private BorderPane borderAdmin;

	@FXML
	private JFXTreeTableView<UserPOJO> usersTable;

	@FXML
	private JFXTextField filter;

	@FXML
	private JFXButton deleteUserAdmin;

	@FXML
	private JFXButton deleteMealAdmin;

	@FXML
	private JFXButton deletePlanAdmin;

	@FXML
	private JFXListView<Label> nutPlansList;

	@FXML
	private JFXListView<Label> mealPlanList;

	@FXML
	private Hyperlink logoutLink;

	@FXML
	private Label errorLabel;

	private static UserPOJO rowData;

	private static NutPlanPOJO nutPlan;

	private static MealPOJO meal;

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminPageController.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usersTable.getColumns().clear();
		updateTable();
		LOGGER.info("Admin page loaded");
	}

	@SuppressWarnings("unchecked")
	private void updateTable() {
		List<UserPOJO> userList;
		rowData = null;
		usersTable.getColumns().clear();
		nutPlansList.getItems().clear();
		mealPlanList.getItems().clear();
		try {
			userList = UserServices.getAllUsers();
			JFXTreeTableColumn<UserPOJO, String> nameCol = new JFXTreeTableColumn<>("Name");
			nameCol.setCellValueFactory(
					new Callback<TreeTableColumn.CellDataFeatures<UserPOJO, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UserPOJO, String> param) {
							return new SimpleStringProperty(param.getValue().getValue().getName());
						}
					});
			nameCol.prefWidthProperty().bind(usersTable.widthProperty().divide(1));

			ObservableList<UserPOJO> users = FXCollections.observableArrayList(userList);
			TreeItem<UserPOJO> root = new RecursiveTreeItem<UserPOJO>(users, RecursiveTreeObject::getChildren);
			usersTable.setRoot(root);
			usersTable.setShowRoot(false);
			usersTable.getColumns().setAll(nameCol);
			usersTable.setRowFactory(tv -> {
				JFXTreeTableRow<UserPOJO> row = new JFXTreeTableRow<>();
				row.setOnMouseClicked(event -> {
					nutPlan = null;
					meal = null;
					nutPlansList.getItems().clear();
					mealPlanList.getItems().clear();
					rowData = row.getItem();
					if (rowData != null) {
						List<NutPlanPOJO> planList = null;
						List<MealPOJO> mealList = null;
						try {
							planList = NutPlanServices.getUserPlans(rowData.getUserId());
							if (planList != null) {
								for (NutPlanPOJO plan : planList) {
									Label lbl = new Label(plan.getName());
									nutPlansList.getItems().add(lbl);
									LOGGER.info("PLAN: " + plan.getName());
								}
							}
						} catch (Exception e) {
							LOGGER.error("Error obtaining plans");
						}
						try {
							mealList = MealServices.getUserMeals(rowData.getUserId());
							if (mealList != null) {
								for (MealPOJO meal : mealList) {
									Label lbl = new Label(meal.getName());
									mealPlanList.getItems().add(lbl);
									LOGGER.info("MEAL: " + meal.getName());
								}
							}
						} catch (Exception e) {
							LOGGER.error("Error obtaining meals");
						}
					}
				});
				nutPlansList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label>() {
					@Override
					public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
						nutPlan = null;
						try {
							nutPlan = NutPlanServices.getPlanByName(newValue.getText());
						} catch (Exception e) {
							LOGGER.error("No plans for user");
						}
					}
				});
				mealPlanList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label>() {
					@Override
					public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
						nutPlan = null;
						try {
							meal = MealServices.getMealByName(newValue.getText());
						} catch (Exception e) {
							LOGGER.error("No meals for user");
						}
					}
				});
				return row;
			});
			filter.textProperty().addListener((o, oldVal, newVal) -> {
				usersTable.setPredicate(user -> user.getValue().getName().contains(newVal));
			});
		} catch (Exception e) {
			LOGGER.error("Error getting users! " + e);
		}
	}

	@FXML
	void processLogout(ActionEvent event) {
		LoginApp.getInstance().setLoggedUser(null);
		LoginController.setUser(null);
		LoginApp.getInstance().gotoLogin();
	}

	@FXML
	void deleteMealAdmin(ActionEvent event) {
		if (meal != null) {
			try {
				JFXDialogLayout content = new JFXDialogLayout();
				JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
				content.setHeading(new Text("Are you sure?"));
				JFXButton delete = new JFXButton("YES");
				delete.setButtonType(ButtonType.RAISED);
				delete.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							MealServices.deleteMeal(meal);
							updateTable();
							dialog.close();
						} catch (Exception e) {
							LOGGER.error("Error deleting user");
						}
					}
				});
				JFXButton noDelete = new JFXButton("NO");
				noDelete.setButtonType(ButtonType.RAISED);
				noDelete.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						dialog.close();
					}
				});
				HBox hbox = new HBox(10);
				hbox.getChildren().addAll(delete, noDelete);
				content.setActions(hbox);
				dialog.toFront();
				dialog.show();
			} catch (Exception e) {
				errorLabel.setText("Please select a meal first!");
				animateMessage(errorLabel);
				LOGGER.error("Error deleting meal");
			}
		} else {
			errorLabel.setText("Please select a meal first");
			animateMessage(errorLabel);
		}
	}

	@FXML
	void deletePlanAdmin(ActionEvent event) {
		if (nutPlan != null) {
			try {
				JFXDialogLayout content = new JFXDialogLayout();
				JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
				content.setHeading(new Text("Are you sure?"));
				JFXButton delete = new JFXButton("YES");
				delete.setButtonType(ButtonType.RAISED);
				delete.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							NutPlanServices.deletePlan(nutPlan);
							updateTable();
							dialog.close();
						} catch (Exception e) {
							LOGGER.error("Error deleting user");
						}
					}
				});
				JFXButton noDelete = new JFXButton("NO");
				noDelete.setButtonType(ButtonType.RAISED);
				noDelete.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						dialog.close();
					}
				});
				HBox hbox = new HBox(10);
				hbox.getChildren().addAll(delete, noDelete);
				content.setActions(hbox);
				dialog.toFront();
				dialog.show();
			} catch (Exception e) {
				errorLabel.setText("Please select a plan first!");
				animateMessage(errorLabel);
				LOGGER.error("Error deleting plan");
			}
		} else {
			errorLabel.setText("Please select a plan first");
			animateMessage(errorLabel);
		}
	}

	@FXML
	void deleteUser(ActionEvent event) {
		if (rowData != null) {
			try {
				JFXDialogLayout content = new JFXDialogLayout();
				JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
				content.setHeading(new Text(
						"Are you sure? This will delete all the Nutrition Plans and Meals related to this user!"));
				JFXButton delete = new JFXButton("YES");
				delete.setButtonType(ButtonType.RAISED);
				delete.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							UserServices.deleteUser(rowData.getUserId());
							updateTable();
							dialog.close();
						} catch (Exception e) {
							LOGGER.error("Error deleting user");
						}
					}
				});
				JFXButton noDelete = new JFXButton("NO");
				noDelete.setButtonType(ButtonType.RAISED);
				noDelete.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						dialog.close();
					}
				});
				HBox hbox = new HBox(10);
				hbox.getChildren().addAll(delete, noDelete);
				content.setActions(hbox);
				dialog.toFront();
				dialog.show();
			} catch (Exception e) {
				errorLabel.setText("Please select a user first!");
				animateMessage(errorLabel);
				LOGGER.error("Error deleting user");
			}
		} else {
			errorLabel.setText("Please select a user first");
			animateMessage(errorLabel);
		}
	}

	private void animateMessage(Label message) {
		FadeTransition ft = new FadeTransition(new Duration(1000), message);
		ft.setFromValue(0.0);
		ft.setToValue(1);
		ft.play();
	}

}
