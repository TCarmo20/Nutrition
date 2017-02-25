package carmo.tiago.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import carmo.tiago.persistence.UserEntity;
import carmo.tiago.services.UserServices;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
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
	private UserEntity loggedUser;

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
		primaryStage.getIcons().add(new Image("/icon.png"));
		try {
			stage = primaryStage;
			stage.setFullScreen(true);
			gotoLogin();
			primaryStage.show();
		} catch (Exception ex) {
			Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public boolean userLogging(String email, String password) throws Exception {
		UserEntity user = UserServices.getUserByEmail(email);
		if (user.getPassword().equals(password)) {
			setLoggedUser(user);
			gotoProfile();
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

	private void gotoProfile() {
		try {
			replaceSceneContent("/Profile.fxml");
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
		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		if (scene == null) {
			FadeTransition ft = new FadeTransition(new Duration(3000), page);
			ft.setFromValue(0.0);
			ft.setToValue(1);
			ft.play();
			scene = new Scene(page, dim.getWidth(), dim.getHeight());
			scene.getStylesheets().add(getClass().getResource("/demo.css").toExternalForm());
			stage.setScene(scene);
		} else {
			stage.getScene().setRoot(page);
		}
		stage.sizeToScene();
		return page;
	}

	public void setLoggedUser(UserEntity loggedUser) {
		this.loggedUser = loggedUser;
	}

	public UserEntity getLoggedUser() {
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

	public void exitApp() {
		Platform.exit();
	}

}