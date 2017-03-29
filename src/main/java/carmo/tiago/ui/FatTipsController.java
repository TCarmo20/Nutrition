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

import carmo.tiago.services.FatPOJO;
import carmo.tiago.services.FatServices;
import carmo.tiago.services.FoodPOJO;
import carmo.tiago.services.FoodServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FatTipsController implements Initializable {

	@FXML
	private AnchorPane HomePage;

	@FXML
	private StackPane stackPane;

	@FXML
	private Group fatGroup;

	@FXML
	private VBox vBoxFat;

	@FXML
	private JFXListView<Label> listView;

	@FXML
	private JFXTextField caloriesFat;

	@FXML
	private JFXTextField proteinFat;

	@FXML
	private JFXTextField carbsFat;

	@FXML
	private JFXTextField fatFat;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;

	private HamburgerBasicCloseTransition burgerTask;

	private static FatTipsController instance;

	private static final Logger LOGGER = LoggerFactory.getLogger(FatTipsController.class);

	public FatTipsController() {
		instance = this;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
			List<FatPOJO> list = FatServices.getAllFat();
			for (FatPOJO fat : list) {
				if (fat.getName().equals("Avocado")) {
					Label lbl = new Label("Avocado");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Fat/avocado.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (fat.getName().equals("Almonds")) {
					Label lbl = new Label("Almonds");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Fat/almonds.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (fat.getName().equals("Nuts")) {
					Label lbl = new Label("Nuts");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Fat/nut.png", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (fat.getName().equals("Olive oil")) {
					Label lbl = new Label("Olive oil");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Fat/olive.png", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (fat.getName().equals("Coconut oil")) {
					Label lbl = new Label("Coconut oil");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Fat/coconut.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (fat.getName().equals("Chia seeds")) {
					Label lbl = new Label("Chia seeds");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Fat/chia.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (fat.getName().equals("Pistachio")) {
					Label lbl = new Label("Pistachio");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Fat/pistachio.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (fat.getName().equals("Linseed oil")) {
					Label lbl = new Label("Linseed oil");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Fat/linseed.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				}
			}
			listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label>() {
				@Override
				public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
					FoodPOJO fat = FoodServices.getFoodByName(newValue.getText());
					proteinFat.setText(fat.getProtein());
					carbsFat.setText(fat.getCarbs());
					fatFat.setText(fat.getFat());
					caloriesFat.setText(fat.getCalories());
				}
			});
		} catch (IOException e) {
			LOGGER.error("Error initializing Home Page's drawer: " + e);
		}
		LOGGER.info("Home Page initialized");
	}

	public static FatTipsController getInstance() {
		return instance;
	}

}
