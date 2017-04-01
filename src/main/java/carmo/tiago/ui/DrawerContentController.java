package carmo.tiago.ui;

import java.net.URL;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.restfb.types.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Tiago Carmo
 *
 */
public class DrawerContentController implements Initializable {

	@FXML
	private StackPane stackPane;

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
	private JFXToggleButton startMealToggle;

	@FXML
	public Label nameLabel;

	private static DrawerContentController instance;

	private static final Logger LOGGER = LoggerFactory.getLogger(DrawerContentController.class);

	public DrawerContentController() {
		instance = this;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LogoutManuBar.setGraphic(new ImageView(new Image("Pictures/logout.png",25, 25, true, false)));
		LogoutManuBar.setContentDisplay(ContentDisplay.LEFT);
		
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

		JFXButton mealBtn = new JFXButton("MEAL PREP");
		mealBtn.setButtonType(ButtonType.RAISED);
		mealBtn.setPrefWidth(198);
		mealBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				processMealPrep();
			}
		});

		foodList.addAnimatedNode(nutritionTipsBtn);
		foodList.addAnimatedNode(proteinBtn);
		foodList.addAnimatedNode(carbsBtn);
		foodList.addAnimatedNode(fatBtn);
		foodList.addAnimatedNode(mealBtn);

		startMealToggle.setSelected(LoginApp.getInstance().mealStart.get());
		startMealToggle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (startMealToggle.isSelected()) {
					try {
						JFXDialogLayout content = new JFXDialogLayout();
						JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
						content.setHeading(new Text("Meal name: \n\n"));
						JFXTextField name = new JFXTextField();
						name.setPromptText("Name");
						name.setPrefWidth(150);
						Label errorLabel = new Label();
						errorLabel.setPrefWidth(150);
						JFXButton prepareMeal = new JFXButton("CONTINUE");
						prepareMeal.setButtonType(ButtonType.RAISED);
						prepareMeal.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								startMealToggle.setText("FINISH MEAL");
								LoginApp.getInstance().setMealStart(true, name.getText());
								dialog.close();
							}
						});
						JFXButton cancelMeal = new JFXButton("CANCEL");
						cancelMeal.setButtonType(ButtonType.RAISED);
						cancelMeal.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								startMealToggle.fire();
								dialog.close();
							}
						});
						HBox hbox = new HBox(10);
						hbox.getChildren().addAll(name, errorLabel);
						content.setBody(hbox);
						content.setActions(prepareMeal, cancelMeal);
						dialog.show();
					} catch (NullPointerException e) {
						LOGGER.error("Dialog null");
					}
				} else {
					startMealToggle.setText("START MEAL");
					LoginApp.getInstance().setMealStart(false, null);
				}
			}
		});
		if (startMealToggle.isSelected()) {
			startMealToggle.setText("FINISH MEAL");
		} else {
			startMealToggle.setText("START MEAL");
		}
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

	private void processMealPrep() {
		LoginApp.getInstance().gotoMealPrep();
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
