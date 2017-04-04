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
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import carmo.tiago.services.MealPOJO;
import carmo.tiago.services.MealServices;
import javafx.animation.FadeTransition;
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

	private HamburgerBasicCloseTransition burgerTask;

	private MealPOJO meal;

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

		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label>() {
			@Override
			public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
				fadeMessages();
				try {
					meal = MealServices.getMealByName(newValue.getText());
					
					double proteinAmount = Double.valueOf(meal.getAmountProtein());
					double carbsamount = Double.valueOf(meal.getAmountCarbs());
					double fatamount = Double.valueOf(meal.getAmountFat());

					double proteinInProtein = Double.valueOf(meal.getProtein().getProtein());
					double carbsInProtein = Double.valueOf(meal.getProtein().getCarbs());
					double fatInProtein = Double.valueOf(meal.getProtein().getFat());
					double caloriesInprotein = Double.valueOf(meal.getProtein().getCalories());

					double protein1 = (proteinInProtein * proteinAmount) / 100;
					double carbs1 = (carbsInProtein * proteinAmount) / 100;
					double fat1 = (fatInProtein * proteinAmount) / 100;
					double calories1 = (caloriesInprotein * proteinAmount) / 100;

					double proteinInCarbs = Double.valueOf(meal.getCarbs().getProtein());
					double carbsInCarbs = Double.valueOf(meal.getCarbs().getCarbs());
					double fatInCarbs = Double.valueOf(meal.getCarbs().getFat());
					double caloriesInCarbs = Double.valueOf(meal.getCarbs().getCalories());

					double protein2 = (proteinInCarbs * carbsamount) / 100;
					double carbs2 = (carbsInCarbs * carbsamount) / 100;
					double fat2 = (fatInCarbs * carbsamount) / 100;
					double calories2 = (caloriesInCarbs * carbsamount) / 100;

					double proteinInFat = Double.valueOf(meal.getFat().getProtein());
					double carbsInFat = Double.valueOf(meal.getFat().getCarbs());
					double fatInFat = Double.valueOf(meal.getFat().getFat());
					double caloriesInFat = Double.valueOf(meal.getFat().getCalories());

					double protein3 = (proteinInFat * fatamount) / 100;
					double carbs3 = (carbsInFat * fatamount) / 100;
					double fat3 = (fatInFat * fatamount) / 100;
					double calories3 = (caloriesInFat * fatamount) / 100;

					caloriesMeal.setText(String.valueOf(calories1 + calories2 + calories3));
					proteinMeal.setText(String.valueOf(protein1 + protein2 + protein3));
					carbsMeal.setText(String.valueOf(carbs1 + carbs2 + carbs3));
					fatMeal.setText(String.valueOf(fat1 + fat2 + fat3));
				} catch (Exception e) {
					LOGGER.error("Error obtaining meal");
				}
			}
		});
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
			if(LoginApp.getInstance().getStage().isMaximized() || LoginApp.getInstance().getStage().isFullScreen()){
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
	void deletePlan(ActionEvent event) {
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

	private void fadeMessages() {
		errorDeleteMeal.setText("");
	}

	private void animateMessage(Label message) {
		FadeTransition ft = new FadeTransition(new Duration(1000), message);
		ft.setFromValue(0.0);
		ft.setToValue(1);
		ft.play();
	}

}
