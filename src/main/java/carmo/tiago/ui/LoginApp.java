package carmo.tiago.ui;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.restfb.types.User;
import carmo.tiago.services.UserPOJO;
import carmo.tiago.services.UserServices;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Tiago Carmo
 *
 */
public class LoginApp extends Application {

	private static final String PERSISTENCE_UNIT_NAME = "nutrition";

	private EntityManagerFactory emf;

	private EntityManager em;

	private Stage stage;

	private UserPOJO loggedUser;

	private static LoginApp instance;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginApp.class);

	@Override
	public void init() throws Exception {
		setEmf(Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME));
		setEm(getEmf().createEntityManager());
		LOGGER.info("Database prepared");
	}

	public LoginApp() {
		instance = this;
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Nutrition App");
		primaryStage.getIcons().add(new Image("Pictures/icon.png"));
		try {
			stage = primaryStage;
			// stage.setMaximized(true);
			// stage.setFullScreen(true);
			// stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			gotoLogin();
			primaryStage.show();
		} catch (Exception ex) {
			LOGGER.error("Error starting stage: " + ex);
		}
	}

	public void userLogging(String email, String password) throws Exception {
		UserPOJO user = UserServices.getUserByEmailPOJO(email);
		if (user.getPassword().equals(UserServices.encrypt(password))) {
			setLoggedUser(user);
			gotoHomePage();
		} else {
			LOGGER.error("Passwords do not match");
			throw new Exception();
		}
	}

	public void userLoggingFB(User user) throws Exception {
		UserPOJO user2 = UserServices.getUserByEmailPOJO(user.getEmail());
		setLoggedUser(user2);
		gotoHomePage();
		LOGGER.info("Logging in with FB");
	}

	public void goToAddUser() {
		try {
			replaceSceneContent("/AddUser.fxml");
		} catch (Exception ex) {
			LOGGER.error("Error changing scene: " + ex);
		}
	}

	private void gotoHomePage() {
		try {
			replaceSceneContent("/HomePage.fxml");
		} catch (Exception ex) {
			LOGGER.error("Error changing scene: " + ex);
		}
	}

	public void gotoLogin() {
		try {
			replaceSceneContent("/Login.fxml");
		} catch (Exception ex) {
			LOGGER.error("Error changing scene: " + ex);
		}
	}

	public void gotoMyPlans() {
		try {
			replaceSceneContent("/MyPlans.fxml");
		} catch (Exception ex) {
			LOGGER.error("Error changing scene: " + ex);
		}
	}

	public void gotoUpdateUserScreen() {
		try {
			replaceSceneContent("/UpdateDetails.fxml");
		} catch (Exception ex) {
			LOGGER.error("Error changing scene: " + ex);
		}
	}

	public void gotoProteinTips() {
		try {
			replaceSceneContent("/ProteinTips.fxml");
		} catch (Exception ex) {
			LOGGER.error("Error changing scene: " + ex);
		}
	}

	public void gotoCarbsTips() {
		try {
			replaceSceneContent("/CarbsTips.fxml");
		} catch (Exception ex) {
			LOGGER.error("Error changing scene: " + ex);
		}
	}

	public void gotoFatTips() {
		try {
			replaceSceneContent("/FatTips.fxml");
		} catch (Exception ex) {
			LOGGER.error("Error changing scene: " + ex);
		}
	}

	private void replaceSceneContent(String fxml) throws Exception {
		Parent page = FXMLLoader.load(getClass().getResource(fxml), null, new JavaFXBuilderFactory());
		Scene scene = stage.getScene();
		if (scene == null) {
			FadeTransition ft = new FadeTransition(new Duration(3000), page);
			ft.setFromValue(0.0);
			ft.setToValue(1);
			ft.play();
			scene = new Scene(page);
			scene.getStylesheets().add(getClass().getResource("/demo.css").toExternalForm());
			stage.setScene(scene);
		} else {
			FadeTransition ft = new FadeTransition(new Duration(1000), page);
			ft.setFromValue(0.0);
			ft.setToValue(1);
			ft.play();
			stage.getScene().setRoot(page);
		}
	}

	public void setLoggedUser(UserPOJO loggedUser) {
		this.loggedUser = loggedUser;
	}

	public UserPOJO getLoggedUser() {
		return loggedUser;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public static LoginApp getInstance() {
		return instance;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}