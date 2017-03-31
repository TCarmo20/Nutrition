package carmo.tiago.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;

	private JFXDialog dialog;

	private HamburgerBasicCloseTransition burgerTask;

	private static HomePageController instance;

	private static final Logger LOGGER = LoggerFactory.getLogger(HomePageController.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
					dialog.close();
				}
			});
			loadDialog();
		} catch (IOException e) {
			LOGGER.error("Error initializing Home Page's drawer: " + e);
		}
		LOGGER.info("Home Page initialized");
	}

	public HomePageController() {
		instance = this;
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

	public static HomePageController getInstance() {
		return instance;
	}

}
