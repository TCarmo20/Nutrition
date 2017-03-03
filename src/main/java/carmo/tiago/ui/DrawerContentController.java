package carmo.tiago.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import carmo.tiago.services.UserServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class DrawerContentController implements Initializable {

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

	@FXML
	protected void processCreatePlan() {
		try {
			List<String> choices = new ArrayList<>();
			choices.add("Hipertrofy");
			choices.add("Maintenance");
			choices.add("Fat Loss");
			ChoiceDialog<String> dialog = new ChoiceDialog<>("Hipertrofy", choices);
			dialog.setTitle("Create Nutrition Plan");
			dialog.setHeaderText(null);
			dialog.setContentText("Choose your objective:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				UserServices.createPlan(result.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void processUpdateUser() {

	}

	@FXML
	protected void processMyPlans() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameLabel.setText("Welcome, " + LoginApp.getInstance().getLoggedUser().getName());
	}

}
