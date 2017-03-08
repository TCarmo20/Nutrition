package carmo.tiago.ui;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tiago Carmo
 *
 */
public class DrawerContentController implements Initializable {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private VBox vBoxMenuBar;

	@FXML
	private ImageView imageViewMenuBar;

	@FXML
	private JFXButton CreatePlanMenuBar;

	@FXML
	private JFXButton MyPlansMenuBar;

	@FXML
	private JFXButton UpdateDetailsMenuBar;

	@FXML
	private JFXButton LogoutManuBar;

	@FXML
	private Label nameLabel;

	@FXML
	protected void processLogout() {
		LoginApp.getInstance().setLoggedUser(null);
		LoginApp.getInstance().gotoLogin();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameLabel.setText("Welcome, " + LoginApp.getInstance().getLoggedUser().getName());
	}

}
