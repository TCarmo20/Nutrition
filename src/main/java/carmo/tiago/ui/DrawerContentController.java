package carmo.tiago.ui;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.restfb.types.User;

import javafx.event.ActionEvent;
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
    private JFXButton MyPlansMenuBar;

    @FXML
    private JFXButton UpdateDetailsMenuBar;

    @FXML
    private JFXButton nutritionTipsBtn;

    @FXML
    private JFXButton LogoutManuBar;

    @FXML 
    public Label nameLabel;
    
    private static DrawerContentController instance;

	private static final Logger LOGGER = LoggerFactory.getLogger(DrawerContentController.class);
	
	public DrawerContentController() {
		instance = this;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		User user = LoginController.getUser();
		if (user != null) {
			LOGGER.info("FB User found: " + user.getName());
			String imageSource = "https://graph.facebook.com/" + user.getId() + "/picture?type=large";
			imageViewMenuBar.setImage(new Image(imageSource, 200, 150, true, false));
			nameLabel.setText("Welcome, " + user.getName());
		} else {
			nameLabel.setText("Welcome, " + LoginApp.getInstance().getLoggedUser().getName());
			imageViewMenuBar.setImage(new Image("Pictures/preloaderWall.jpg", 200, 150, true, false));
			LOGGER.info("FB User not found: " + LoginApp.getInstance().getLoggedUser().getName());
		}
		LOGGER.info("Drawer initialized");
	}

	public ImageView getImageViewMenuBar() {
		return imageViewMenuBar;
	}

	public void setImageViewMenuBar(ImageView imageViewMenuBar1) {
		imageViewMenuBar = imageViewMenuBar1;
	}

	@FXML
	void processLogout(ActionEvent event) {
		LoginApp.getInstance().setLoggedUser(null);
		LoginController.setUser(null);
		LoginApp.getInstance().gotoLogin();
	}

	@FXML
	void processMyPlans(ActionEvent event) {
		LoginApp.getInstance().gotoMyPlans();
	}

	@FXML
	void processUpdateDetails(ActionEvent event) {
		LoginApp.getInstance().gotoUpdateUserScreen();
	}

	@FXML
	void processCreatePlan(ActionEvent event) {
		LoginApp.getInstance().gotoUpdateUserScreen();
	}
	
	@FXML
    void processNutritionTips(ActionEvent event) {
		LoginApp.getInstance().gotoTipsScreen();
    }

	public static DrawerContentController getInstance() {
		return instance;
	}
	
}
