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

import carmo.tiago.services.FoodPOJO;
import carmo.tiago.services.FoodServices;
import carmo.tiago.services.ProteinPOJO;
import carmo.tiago.services.ProteinServices;
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

public class ProteinTipsController implements Initializable {

	@FXML
    private AnchorPane HomePage;

    @FXML
    private StackPane stackPane;

    @FXML
    private Group proteinGroup;

    @FXML
    private VBox vBoxProtein;

    @FXML
    private JFXListView<Label> listView;

    @FXML
    private JFXTextField caloriesProtein;

    @FXML
    private JFXTextField proteinProtein;

    @FXML
    private JFXTextField carbsProtein;

    @FXML
    private JFXTextField fatProtein;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

	private HamburgerBasicCloseTransition burgerTask;

	private static ProteinTipsController instance;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProteinTipsController.class);

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
			List<ProteinPOJO> list = ProteinServices.getAllProtein();
			for (ProteinPOJO protein : list) {
				if (protein.getName().equals("Chicken breast")) {
					Label lbl = new Label("Chicken breast");
					lbl.setGraphic(
							new ImageView(new Image("Pictures/Food/Protein/chickenbreast.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (protein.getName().equals("Turkey breast")) {
					Label lbl = new Label("Turkey breast");
					lbl.setGraphic(
							new ImageView(new Image("Pictures/Food/Protein/turkeybreast.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (protein.getName().equals("Salmon")) {
					Label lbl = new Label("Salmon");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Protein/salmon.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (protein.getName().equals("Tuna")) {
					Label lbl = new Label("Tuna");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Protein/tuna.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (protein.getName().equals("Egg")) {
					Label lbl = new Label("Egg");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Protein/egg.png", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (protein.getName().equals("Milk")) {
					Label lbl = new Label("Milk");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Protein/milk.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (protein.getName().equals("Whey")) {
					Label lbl = new Label("Whey");
					lbl.setGraphic(new ImageView(new Image("Pictures/Food/Protein/whey.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				} else if (protein.getName().equals("Cottage cheese")) {
					Label lbl = new Label("Cottage cheese");
					lbl.setGraphic(
							new ImageView(new Image("Pictures/Food/Protein/cottage.jpg", 200, 150, true, false)));
					lbl.setContentDisplay(ContentDisplay.TOP);
					listView.getItems().add(lbl);
				}
			}
			listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label>() {
				@Override
				public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
					FoodPOJO protein = FoodServices.getFoodByName(newValue.getText());
					proteinProtein.setText(protein.getProtein());
					carbsProtein.setText(protein.getCarbs());
					fatProtein.setText(protein.getFat());
					caloriesProtein.setText(protein.getCalories());
				}
			});
		} catch (IOException e) {
			LOGGER.error("Error initializing Home Page's drawer: " + e);
		}
		LOGGER.info("Home Page initialized");
	}

	public ProteinTipsController() {
		instance = this;
	}

	public static ProteinTipsController getInstance() {
		return instance;
	}

}
