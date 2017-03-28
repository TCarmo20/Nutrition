package carmo.tiago.ui;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXNodesList;
import com.restfb.types.User;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	private JFXButton LogoutManuBar;

	@FXML
	private JFXNodesList foodList;

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

		JFXButton nutritionTipsBtn = new JFXButton("NUTRITION TIPS");
		nutritionTipsBtn.setButtonType(ButtonType.RAISED);
		nutritionTipsBtn.setPrefWidth(198);

		JFXButton proteinBtn = new JFXButton("PROTEIN");
		proteinBtn.setButtonType(ButtonType.RAISED);
		proteinBtn.setPrefWidth(198);
		proteinBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				processProteinTips();
			}
		});

		JFXButton carbsBtn = new JFXButton("CARBS");
		carbsBtn.setButtonType(ButtonType.RAISED);
		carbsBtn.setPrefWidth(198);
		carbsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				processCarbsTips();
			}
		});

		JFXButton fatBtn = new JFXButton("FAT");
		fatBtn.setButtonType(ButtonType.RAISED);
		fatBtn.setPrefWidth(198);
		fatBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				processFatTips();
			}
		});

		foodList.addAnimatedNode(nutritionTipsBtn);
		foodList.addAnimatedNode(proteinBtn);
		foodList.addAnimatedNode(carbsBtn);
		foodList.addAnimatedNode(fatBtn);

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

	private void processProteinTips() {
		LoginApp.getInstance().gotoProteinTips();
	}

	private void processCarbsTips() {
		LoginApp.getInstance().gotoCarbsTips();
	}

	private void processFatTips() {
		LoginApp.getInstance().gotoFatTips();
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

	public static DrawerContentController getInstance() {
		return instance;
	}

}
