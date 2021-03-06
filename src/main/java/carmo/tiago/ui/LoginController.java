package carmo.tiago.ui;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.User;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * 
 * @author Tiago Carmo
 *
 */
public class LoginController implements Initializable {

	@FXML
    private BorderPane Login;

    @FXML
    private Group groupLogin;

    @FXML
    private Label errorMessage;

    @FXML
    private JFXTextField userIdLogin;

    @FXML
    private JFXPasswordField passwordLogin;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton addUserLogin;

    @FXML
    private JFXButton facebookBtn;

    @FXML
    private VBox progressBox;

    @FXML
    private ProgressIndicator progressLogin;

	private TextField url;
	private WebView webView;
	public static User user;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@FXML
	protected void processLogin() {
		fadeMessage();
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				progressLogin.setVisible(true);
				LoginApp.getInstance().userLogging(userIdLogin.getText(), passwordLogin.getText());
				LOGGER.info("User logged in");
				return null;
			}
		};
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				progressLogin.setVisible(false);
				if(LoginApp.getInstance().getLoggedUser().getProfile().equals("Admin")){
					LoginApp.getInstance().gotoAdminPage();
				} else {
					LoginApp.getInstance().gotoHomePage();
				}
			}
		});
		task.setOnFailed(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				progressLogin.setVisible(false);
				errorMessage.setText("Username/Password is wrong!");
				animateMessage(errorMessage);
			}
		});
		new Thread(task).start();
	}

	private void fadeMessage() {
		errorMessage.setText("");
		LOGGER.info("Message cleared");
	}

	private void animateMessage(Label message) {
		FadeTransition ft = new FadeTransition(new Duration(1000), message);
		ft.setFromValue(0.0);
		ft.setToValue(1);
		ft.play();
	}

	@FXML
	protected void processAddUser() {
		LOGGER.info("Changing scene");
		LoginApp.getInstance().goToAddUser();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		LOGGER.info("Login controller initialized");
	}

	@FXML
	protected void processFacebook() {
		String domain = "http://www.google.com";
		String appId = "1030304780447484";
		String authUrl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + appId
				+ "&redirect_uri=" + domain + "&scope=email";
		Stage stage = new Stage(StageStyle.UTILITY);
		url = new TextField();
		url.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode().equals(KeyCode.ENTER))
					webView.getEngine().load(url.getText());
			}
		});
		url.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				webView.getEngine().load(url.getText());
			}
		});
		webView = new WebView();
		final WebEngine webEngine = webView.getEngine();
		webEngine.locationProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
				url.setText(newVal);
				String accessToken;
				if (!newVal.contains("facebook.com")) {
					try {
						String url2 = newVal;
						accessToken = url2.replaceAll(".*#access_token=(.+)&.*", "$1");
						FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
						user = fbClient.fetchObject("me", User.class, Parameter.with("fields", "name,email,picture"));
						stage.close();
						LoginApp.getInstance().userLoggingFB(user);
						LOGGER.info("FB user logged in");
					} catch (Exception e) {
						LOGGER.error("FB user not in DB yet");
						errorMessage.setText("Please add user before using the Facebook login");
						animateMessage(errorMessage);
					}
				}
			}
		});
		webEngine.load(authUrl);
		BorderPane root = new BorderPane();
		root.setTop(url);
		root.setCenter(webView);
		stage.setScene(new Scene(root, 640, 480));
		stage.show();
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		LoginController.user = user;
	}
}
