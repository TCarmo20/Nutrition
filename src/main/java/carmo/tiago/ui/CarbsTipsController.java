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
import carmo.tiago.services.CarbsPOJO;
import carmo.tiago.services.CarbsServices;
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

public class CarbsTipsController implements Initializable {

	@FXML
    private AnchorPane HomePage;

    @FXML
    private StackPane stackPane;

    @FXML
    private Group carbsGroup;

    @FXML
    private VBox vBoxCarbs;

    @FXML
    private JFXListView<Label> listView;

    @FXML
    private JFXTextField caloriesCarbs;

    @FXML
    private JFXTextField proteinCarbs;

    @FXML
    private JFXTextField carbsCarbs;

    @FXML
    private JFXTextField fatCarbs;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

	private HamburgerBasicCloseTransition burgerTask;

	private static CarbsTipsController instance;

	private static final Logger LOGGER = LoggerFactory.getLogger(CarbsTipsController.class);

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
			List<CarbsPOJO> list = CarbsServices.getAllCarbs();
			for (CarbsPOJO carbs : list) {
				if (carbs.getName().equals("Basmati rice")) {
					Label lbl = new Label("Basmati rice");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Carbs/basmati.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (carbs.getName().equals("Sweet potato")) {
					Label lbl = new Label("Sweet potato");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Carbs/sweet.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (carbs.getName().equals("White beans")) {
					Label lbl = new Label("White beans");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Carbs/white.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (carbs.getName().equals("Integral spaghetti")) {
					Label lbl = new Label("Integral spaghetti");
					lbl.setGraphic(
							new ImageView(new Image("Pictures/Food/Carbs/spaghetti.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (carbs.getName().equals("Integral bread")) {
					Label lbl = new Label("Integral bread");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Carbs/bread.JPG", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (carbs.getName().equals("Brocolis")) {
					Label lbl = new Label("Brocolis");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Carbs/brocolis.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (carbs.getName().equals("Oats")) {
					Label lbl = new Label("Oats");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Carbs/oats.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (carbs.getName().equals("Black beans")) {
					Label lbl = new Label("Black beans");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Carbs/black.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				}
			}
			listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label>() {
				@Override
				public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
					FoodPOJO carbs = FoodServices.getFoodByName(newValue.getText());
					proteinCarbs.setText(carbs.getProtein());
					carbsCarbs.setText(carbs.getCarbs());
					fatCarbs.setText(carbs.getFat());
					caloriesCarbs.setText(carbs.getCalories());
				}
			});
		} catch (IOException e) {
			LOGGER.error("Error initializing Home Page's drawer: " + e);
		}
		LOGGER.info("Home Page initialized");
	}

	public CarbsTipsController() {
		instance = this;
	}

	public static CarbsTipsController getInstance() {
		return instance;
	}

}
