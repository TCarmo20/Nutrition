package carmo.tiago.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
 * Main Application. This class handles navigation and user session.
 * 
 * @author Tarun Tyagi
 */
public class LoginApp extends Application {

	private static final String PERSISTENCE_UNIT_NAME = "nutrition";
	private EntityManagerFactory emf;
	private EntityManager em;
	private Stage stage;
	private UserPOJO loggedUser;
	private static LoginApp instance;

	@Override
	public void init() throws Exception {
		setEmf(Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME));
		setEm(getEmf().createEntityManager());
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
			stage.setMaximized(true);
			// stage.setFullScreen(true);
			// stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			gotoLogin();
			primaryStage.show();
		} catch (Exception ex) {
			Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public boolean userLogging(String email, String password) throws Exception {
		UserPOJO user = UserServices.getUserByEmailPOJO(email);
		if (user.getPassword().equals(UserServices.encrypt(password))) {
			setLoggedUser(user);
			gotoHomePage();
			return true;
		} else {
			return false;
		}
	}

	public void userLogout() {
		setLoggedUser(null);
		gotoLogin();
	}

	public void goToAddUser() {
		try {
			replaceSceneContent("/AddUser.fxml");
		} catch (Exception ex) {
			Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void gotoHomePage() {
		try {
			replaceSceneContent("/HomePage.fxml");
		} catch (Exception ex) {
			Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void gotoLogin() {
		try {
			replaceSceneContent("/Login.fxml");
		} catch (Exception ex) {
			Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private Parent replaceSceneContent(String fxml) throws Exception {
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
		return page;
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