package carmo.tiago.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import carmo.tiago.persistence.UserEntity;
import carmo.tiago.services.UserServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Application. This class handles navigation and user session.
 * 
 * @author Tarun Tyagi
 */
public class LoginApp extends Application {

	private static final String PERSISTENCE_UNIT_NAME = "nutrition";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private Stage stage;
	private UserEntity loggedUser;

	private static LoginApp instance;

	public LoginApp() {
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		setEm(emf.createEntityManager());
		instance = this;
	}

	public static LoginApp getInstance() {
		return instance;
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
		try {
			stage = primaryStage;
			gotoLogin();
			primaryStage.show();
		} catch (Exception ex) {
			Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public UserEntity getLoggedUser() {
		return loggedUser;
	}

	public boolean userLogging(String email, String password) throws Exception {
		UserEntity user = UserServices.getUser(email);
		if (user.getPassword().equals(password)) {
			loggedUser = user;
			gotoProfile();
			return true;
		} else {
			return false;
		}
	}

	public void userLogout() {
		loggedUser = null;
		gotoLogin();
	}

	public void goToAddUser() {
		try {
			replaceSceneContent("/addUser.fxml");
		} catch (Exception ex) {
			Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void gotoProfile() {
		try {
			replaceSceneContent("/profile.fxml");
		} catch (Exception ex) {
			Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void gotoLogin() {
		try {
			replaceSceneContent("/login.fxml");
		} catch (Exception ex) {
			Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private Parent replaceSceneContent(String fxml) throws Exception {
		Parent page = (Parent) FXMLLoader.load(getClass().getResource(fxml), null, new JavaFXBuilderFactory());
		Scene scene = stage.getScene();
		if (scene == null) {
			scene = new Scene(page, 600, 500);
			scene.getStylesheets().add(getClass().getResource("/demo.css").toExternalForm());
			stage.setScene(scene);
		} else {
			stage.getScene().setRoot(page);
		}
		stage.sizeToScene();
		return page;
	}

	public static EntityManager getEm() {
		return em;
	}

	public static void setEm(EntityManager em) {
		LoginApp.em = em;
	}
}