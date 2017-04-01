package carmo.tiago.ui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import carmo.tiago.services.MealPOJO;
import carmo.tiago.services.MealServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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

	private HamburgerBasicCloseTransition burgerTask;

	private MealPOJO meal;

	private static final Logger LOGGER = LoggerFactory.getLogger(MealPrepController.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeHomePage();
		updateTable();
	}

	private void updateTable() {
		List<MealPOJO> list = MealServices.getUserMeals(LoginApp.getInstance().getLoggedUser().getUserId());
		for (MealPOJO meal : list) {
			Label lbl = new Label(meal.getName());
			listView.getItems().add(lbl);
		}
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label>() {
			@Override
			public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
				meal = MealServices.getMealByName(newValue.getText());
				System.out.println("Cal1: " + meal.getProtein().getCalories());
				System.out.println("Cal2: " + meal.getCarbs().getCalories());
				System.out.println("Cal3: " + meal.getFat().getCalories());

				caloriesMeal.setText(String.valueOf(meal.getProtein().getCalories())
						+ String.valueOf(meal.getCarbs().getCalories()) + String.valueOf(meal.getFat().getCalories()));
				proteinMeal.setText(
						meal.getProtein().getProtein() + meal.getCarbs().getProtein() + meal.getFat().getProtein());
				carbsMeal.setText(meal.getProtein().getCarbs() + meal.getCarbs().getCarbs() + meal.getFat().getCarbs());
				fatMeal.setText(meal.getProtein().getFat() + meal.getCarbs().getFat() + meal.getFat().getFat());
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
		} catch (IOException e) {
			LOGGER.error("Error initializing Home Page's drawer: " + e);
		}
		LOGGER.info("Home Page initialized");
	}

}
