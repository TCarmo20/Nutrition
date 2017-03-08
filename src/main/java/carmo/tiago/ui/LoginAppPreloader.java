package carmo.tiago.ui;

import com.jfoenix.controls.JFXProgressBar;

import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Tiago Carmo
 *
 */
public class LoginAppPreloader extends Preloader {
	
	@FXML
    private StackPane stackPane;

    @FXML
    private JFXProgressBar progressBar;
    
	private Stage preloaderStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.preloaderStage = primaryStage;
		preloaderStage.getIcons().add(new Image("/Pictures/icon.png"));
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
