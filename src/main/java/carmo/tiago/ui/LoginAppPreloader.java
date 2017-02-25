package carmo.tiago.ui;

import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginAppPreloader extends Preloader {
	private Stage preloaderStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.preloaderStage = primaryStage;
		preloaderStage.getIcons().add(new Image("/icon.png"));
		preloaderStage.initStyle(StageStyle.UNDECORATED);
		Parent page = (Parent) FXMLLoader.load(getClass().getResource("/Preloader.fxml"), null,
				new JavaFXBuilderFactory());
		Scene scene = new Scene(page, 600, 500);
		scene.getStylesheets().add(getClass().getResource("/demo.css").toExternalForm());
		primaryStage.setTitle("Nutrition App");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
		if (stateChangeNotification.getType() == Type.BEFORE_START) {
			preloaderStage.hide();
		}
	}
}
