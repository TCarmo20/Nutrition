package carmo.tiago.ui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import carmo.tiago.services.DayServices;
import carmo.tiago.services.MealPOJO;
import carmo.tiago.services.MealServices;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MealPrepController implements Initializable {

	@FXML
	private AnchorPane HomePage;

	@FXML
	private StackPane stackPane;

	@FXML
	private Group mealPrepGroup;

	@FXML
	private VBox vBoxMeals;

	@FXML
	private JFXListView<Label> listView;

	@FXML
	private JFXTextField proteinMeal;

	@FXML
	private JFXTextField carbsMeal;

	@FXML
	private JFXTextField fatMeal;

	@FXML
	private JFXTextField caloriesMeal;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;

	@FXML
	private Label errorDeleteMeal;

	@FXML
	private JFXButton deleteMeal;

	@FXML
	private JFXTextArea ingredientsMeal;

	@FXML
	private JFXButton addToDay;

	@FXML
	private JFXListView<Label> dayMeals;

	@FXML
	private JFXButton dayTotal;

	@FXML
	private JFXButton deleteMealDay;

	@FXML
	private JFXButton saveDay;

	@FXML
	private JFXButton allDaysBtn;

	private HamburgerBasicCloseTransition burgerTask;

	private MealPOJO meal;

	private Double[] totals = new Double[4];

	private static final Logger LOGGER = LoggerFactory.getLogger(MealPrepController.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeHomePage();
		listView.getItems().clear();
		updateTable();
	}

	private void updateTable() {
		listView.getItems().clear();
		List<MealPOJO> list = null;
		try {
			list = MealServices.getUserMeals(LoginApp.getInstance().getLoggedUser().getUserId());
			if (list.isEmpty()) {
				addToDay.setDisable(true);
				deleteMeal.setDisable(true);
				LOGGER.error("No Meals");
			} else {
				for (MealPOJO meal : list) {
					Label lbl = new Label(meal.getName());
					listView.getItems().add(lbl);
				}
			}
		} catch (Exception e1) {
			LOGGER.error("Error obtaining meals");
		}
	}

	public void initializeHomePage() {
		try {
			StackPane anchor = FXMLLoader.load(getClass().getResource("/DrawerContent.fxml"));
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
			listView.getItems().clear();
			dayMeals.getItems().clear();
			listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label>() {
				@Override
				public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
					listView.getSelectionModel().clearSelection();
					fadeMessages();
					try {
						meal = MealServices.getMealByName(newValue.getText());
						caloriesMeal.setText(String.valueOf(meal.getTotalCal()));
						proteinMeal.setText(String.valueOf(meal.getTotalProt()));
						carbsMeal.setText(String.valueOf(meal.getTotalCarb()));
						fatMeal.setText(String.valueOf(meal.getTotalFat()));
						ingredientsMeal.setText(meal.getProtein().getName() + " - " + meal.getAmountProtein() + "gr\n"
								+ meal.getCarbs().getName() + " - " + meal.getAmountCarbs() + "gr\n"
								+ meal.getFat().getName() + " - " + meal.getAmountFat() + "gr");
					} catch (Exception e) {
						LOGGER.error("Error obtaining meal");
					}
				}
			});
			
			if (LoginApp.getInstance().getStage().isMaximized() || LoginApp.getInstance().getStage().isFullScreen()) {
				burgerTask.setRate(burgerTask.getRate() * -1);
				burgerTask.play();
				drawer.open();
			}
		} catch (IOException e) {
			LOGGER.error("Error initializing Home Page's drawer: " + e);
		}
		LOGGER.info("Home Page initialized");
	}

	@FXML
	private void deletePlan(ActionEvent event) {
		fadeMessages();
		if (meal != null) {
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
						MealServices.deleteMeal(meal);
						updateTable();
						meal = null;
						caloriesMeal.setText("");
						proteinMeal.setText("");
						carbsMeal.setText("");
						fatMeal.setText("");
						ingredientsMeal.setText("");
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
						LOGGER.error("Error deleting plan!" + e1);
						e1.printStackTrace();
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
			errorDeleteMeal.setText("Please select a meal first");
			animateMessage(errorDeleteMeal);
		}
	}

	@FXML
	private void addMealToDay(ActionEvent event) {
		if (meal != null && totals[0] == null) {
			Label lbl = new Label(meal.getName());
			dayMeals.getItems().add(lbl);
			totals[0] = Double.valueOf(caloriesMeal.getText());
			totals[1] = Double.valueOf(proteinMeal.getText());
			totals[2] = Double.valueOf(carbsMeal.getText());
			totals[3] = Double.valueOf(fatMeal.getText());
		} else if (meal != null && totals[0] != null) {
			Label lbl = new Label(meal.getName());
			dayMeals.getItems().add(lbl);
			totals[0] = totals[0] + Double.valueOf(caloriesMeal.getText());
			totals[1] = totals[1] + Double.valueOf(proteinMeal.getText());
			totals[2] = totals[2] + Double.valueOf(carbsMeal.getText());
			totals[3] = totals[3] + Double.valueOf(fatMeal.getText());
		}
	}

	private void fadeMessages() {
		errorDeleteMeal.setText("");
	}

	private void animateMessage(Label message) {
		FadeTransition ft = new FadeTransition(new Duration(1000), message);
		ft.setFromValue(0.0);
		ft.setToValue(1);
		ft.play();
	}

	@FXML
	private void calculate(ActionEvent event) {
		if (!dayMeals.getItems().isEmpty()) {
			caloriesMeal.setText(String.valueOf(totals[0]));
			proteinMeal.setText(String.valueOf(totals[1]));
			carbsMeal.setText(String.valueOf(totals[2]));
			fatMeal.setText(String.valueOf(totals[3]));
			ingredientsMeal.setText("");
		}
	}

	@FXML
	private void deleteMealFromDay(ActionEvent event) {
		dayMeals.getItems().clear();
		totals[0] = null;
		totals[1] = null;
		totals[2] = null;
		totals[3] = null;
	}

	@FXML
	private void saveDay(ActionEvent event) {
		if (!dayMeals.getItems().isEmpty()) {
			try {
				DayServices.addDay(LoginApp.getInstance().getLoggedUser(), dayMeals, totals);
				JFXSnackbar snack = new JFXSnackbar(stackPane);
				snack.enqueue(new SnackbarEvent("Day added to your days".toUpperCase()));
			} catch (Exception e) {
				LOGGER.error("Error saving Day. Exception: " + e);
			}
		}
	}

	@FXML
	void gotoAllDays(ActionEvent event) {
		LoginApp.getInstance().gotoAllDays();
	}

}
