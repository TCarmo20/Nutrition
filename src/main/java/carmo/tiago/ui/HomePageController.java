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

	public HomePageController() {
		instance = this;
	}

	private void loadDialog() {
		JFXDialogLayout content = new JFXDialogLayout();
		dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
		dialog.overlayCloseProperty().set(false);
		content.setHeading(new Text("Welcome to the Nutrition app"));
		content.setBody(new Text(
				"To start please press the 'OK' button or the three lines at the top right corner of the page. "
				+ "\nThis will open the menu drawer where you will be able to navigate to all the screens on the app."
				+ "\nApp created by Tiago Carmo. Any questions or suggestions on how to improve the app, please email me!"
				+ "\ntcarmo20@gmail.com"));
		JFXButton close = new JFXButton("OK");
		close.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
				burgerTask.setRate(burgerTask.getRate() * -1);
				burgerTask.play();
				drawer.open();
			}
		});
		content.setActions(close);
		dialog.show();
	}

	public static HomePageController getInstance() {
		return instance;
	}

}
