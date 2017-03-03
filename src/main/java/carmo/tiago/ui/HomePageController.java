package carmo.tiago.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * Java FX FXML Controller.
 * 
 * @author Tarun Tyagi
 */
public class HomePageController implements Initializable {

	@FXML
	private StackPane HomePage;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			AnchorPane anchor = FXMLLoader.load(getClass().getResource("/DrawerContent.fxml"));
			drawer.setSidePane(anchor);
			HamburgerBackArrowBasicTransition hamTran = new HamburgerBackArrowBasicTransition(hamburger);
			hamTran.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
				hamTran.setRate(hamTran.getRate()*-1);
				hamTran.play();
				if(drawer.isShown()){
					drawer.close();
				} else {
					drawer.open();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
