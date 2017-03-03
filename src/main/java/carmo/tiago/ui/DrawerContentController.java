package carmo.tiago.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import carmo.tiago.services.UserServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

	@FXML
	protected void processCreatePlan() {
		try {
			HomePageController.createPlan();
//			List<String> choices = new ArrayList<>();
//			choices.add("Hipertrofy");
//			choices.add("Maintenance");
//			choices.add("Fat Loss");
//			ChoiceDialog<String> dialog = new ChoiceDialog<String>("Hipertrofy", choices);
//			dialog.getDialogPane().getStylesheets().add(getClass().getResource("/demo.css").toExternalForm());
//			dialog.setTitle("Create Nutrition Plan");
//			dialog.setHeaderText(null);
//			dialog.setContentText("Choose your objective:");
//			dialog.initStyle(StageStyle.UTILITY);
//			dialog.initModality(Modality.WINDOW_MODAL);
//			Optional<String> result = dialog.showAndWait();
//			if (result.isPresent()) {
//				UserServices.createPlan(result.get());
//			}
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
