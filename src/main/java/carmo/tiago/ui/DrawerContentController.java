package carmo.tiago.ui;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.restfb.types.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		User user = LoginController.getUser();
		if(user != null){
			String imageSource = "https://graph.facebook.com/" + user.getId() + "/picture?type=large";
			imageViewMenuBar.setImage(new Image(imageSource, 200, 150, false,false));
			nameLabel.setText("Welcome, " + user.getName());
		} else {
			nameLabel.setText("Welcome, " + LoginApp.getInstance().getLoggedUser().getName());
			imageViewMenuBar.setImage(new Image("Pictures/preloaderWall.jpg"));
		}
	}

	public ImageView getImageViewMenuBar() {
		return imageViewMenuBar;
	}

	public void setImageViewMenuBar(ImageView imageViewMenuBar1) {
		imageViewMenuBar = imageViewMenuBar1;
	}

}
