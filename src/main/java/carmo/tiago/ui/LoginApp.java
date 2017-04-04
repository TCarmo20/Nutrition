package carmo.tiago.ui;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.restfb.types.User;

import carmo.tiago.services.MealPOJO;
import carmo.tiago.services.MealServices;
import carmo.tiago.services.UserPOJO;
import carmo.tiago.services.UserServices;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

	public BooleanProperty mealStart = new SimpleBooleanProperty();

	private static MealPOJO meal;

	@Override
	public void init() throws Exception {
		setEmf(Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME));
		setEm(getEmf().createEntityManager());
		LOGGER.info("Database prepared");
		mealStart.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				try {
					ProteinTipsController.getInstance().addToMealProtein.setDisable(!mealStart.get());
					ProteinTipsController.getInstance().amountProtein.setDisable(!mealStart.get());
				} catch (NullPointerException e) {
					LOGGER.info("Protein button is null: " + e);
				}
				try {
					CarbsTipsController.getInstance().addToMealCarbs.setDisable(!mealStart.get());
					CarbsTipsController.getInstance().amountCarbs.setDisable(!mealStart.get());
				} catch (NullPointerException e1) {
					LOGGER.info("Carbs button is null: " + e1);
				}
				try {
					FatTipsController.getInstance().addToMealFat.setDisable(!mealStart.get());
					FatTipsController.getInstance().amountFat.setDisable(!mealStart.get());
				} catch (NullPointerException e2) {
					LOGGER.info("Fat buttin is null: " + e2);
				}
			}
		});
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
//			stage.setMaximized(true);
//			stage.setFullScreen(true);
//			stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			gotoLogin();
			primaryStage.show();
		} catch (Exception ex) {
			LOGGER.error("Error starting stage: " + ex);
		}
	}

	public BooleanProperty getMealStart() {
		return mealStart;
	}

	public void setMealStart(boolean mealStart, String name) {
		if (mealStart) {
			this.mealStart.set(true);
			startMeal(name);
		} else {
			this.mealStart.set(false);
			finishMeal();
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
			ex.printStackTrace();
			LOGGER.error("Error changing scene: " + ex);
		}
	}

	public void gotoLogin() {
		try {
			mealStart.set(false);
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

	public void gotoMealPrep() {
		try {
			replaceSceneContent("/MealPrep.fxml");
		} catch (Exception ex) {
			ex.printStackTrace();
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

	public static void startMeal(String name) {
		meal = new MealPOJO();
		meal.setName(name + " (" + new Date() + ")");
	}

	public MealPOJO getMeal() {
		return meal;
	}

	public void setMeal(MealPOJO meal) {
		LoginApp.meal = meal;
	}

	public static void finishMeal() {
		try {
			MealServices.addMeal(meal);
			LoginApp.getInstance().gotoMealPrep();
		} catch (NullPointerException e) {
			LOGGER.error("Missing ingredients");
		} catch (Exception e) {
			LOGGER.error("Other error");
		}
	}

}