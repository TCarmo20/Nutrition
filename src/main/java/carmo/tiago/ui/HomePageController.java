package carmo.tiago.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import carmo.tiago.services.UserServices;
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
 * Java FX FXML Controller.
 * 
 * @author Tarun Tyagi
 */
public class HomePageController implements Initializable {

	@FXML
    private static StackPane HomePage;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDialog dialog;

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

	public static void createPlan() {
		System.out.println("Chegou!");
		JFXDialogLayout content = new JFXDialogLayout();
		content.setHeading(new Text("Choose your objective"));
		JFXComboBox<String> objectives = new JFXComboBox<String>();
		objectives.getItems().addAll("Hipertrophy","Maintenance","Fat Loss");
		content.setBody(objectives);
		JFXDialog dialog = new JFXDialog(HomePage,content,JFXDialog.DialogTransition.CENTER);
		JFXButton create = new JFXButton("Create");
		create.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					UserServices.getInstance().createPlan(objectives.getSelectionModel().getSelectedItem());
					dialog.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		content.setActions(create);
		dialog.show();

	}

}
