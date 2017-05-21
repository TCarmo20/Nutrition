package carmo.tiago.ui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import carmo.tiago.services.DayPOJO;
import carmo.tiago.services.DayServices;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class DayPageController implements Initializable {

	@FXML
	private AnchorPane HomePage;

	@FXML
	private StackPane stackPane;

	@FXML
	private Group dayGroup;

	@FXML
	private VBox vBoxDays;

	@FXML
	private JFXTreeTableView<DayPOJO> DaysTable;

	@FXML
	private JFXTextField caloriesDay;

	@FXML
	private JFXTextField proteinDay;

	@FXML
	private JFXTextField carbsDay;

	@FXML
	private JFXTextField fatDay;

	@FXML
	private Label deleteExportLabel;

	@FXML
	private JFXButton exportDayBtn;

	@FXML
	private JFXButton deleteDayBtn;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;

	@FXML
	private JFXTextArea ingredientsMeal;

	private HamburgerBasicCloseTransition burgerTask;

	private DayPOJO rowData;

	private static final Logger LOGGER = LoggerFactory.getLogger(DayPageController.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeHomePage();
		DaysTable.getColumns().clear();
		updateTable();
	}

	@SuppressWarnings("unchecked")
	private void updateTable() {
		List<DayPOJO> dayList;
		try {
			dayList = DayServices.getUserDays(LoginApp.getInstance().getLoggedUser().getUserId());
			JFXTreeTableColumn<DayPOJO, String> dayCol = new JFXTreeTableColumn<>("Day");
			dayCol.setCellValueFactory(
					new Callback<TreeTableColumn.CellDataFeatures<DayPOJO, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<DayPOJO, String> param) {
							return new SimpleStringProperty(param.getValue().getValue().getDay());
						}
					});
			dayCol.prefWidthProperty().bind(DaysTable.widthProperty().divide(1));
			ObservableList<DayPOJO> days = FXCollections.observableArrayList(dayList);
			TreeItem<DayPOJO> root = new RecursiveTreeItem<DayPOJO>(days, RecursiveTreeObject::getChildren);
			DaysTable.setRoot(root);
			DaysTable.setShowRoot(false);
			DaysTable.getColumns().setAll(dayCol);
			DaysTable.setRowFactory(tv -> {
				JFXTreeTableRow<DayPOJO> row = new JFXTreeTableRow<>();
				row.setOnMouseClicked(event -> {
					ingredientsMeal.setText("");
					rowData = row.getItem();
					if (rowData != null) {
						caloriesDay.setText(String.valueOf(rowData.getTotals()[0]));
						proteinDay.setText(String.valueOf(rowData.getTotals()[1]));
						carbsDay.setText(String.valueOf(rowData.getTotals()[2]));
						fatDay.setText(String.valueOf(rowData.getTotals()[3]));
						for (int i = 0; i < rowData.getDayMeals().getItems().size(); i++) {
							ingredientsMeal.appendText(rowData.getDayMeals().getItems().get(i).getText() + "\n");
						}
					}
				});
				return row;
			});

		} catch (Exception e) {
			LOGGER.error("Error building tableview: " + e);
		}
	}

	private void initializeHomePage() {
		try {
			StackPane anchor = FXMLLoader.load(getClass().getResource("/DrawerContent.fxml"));
			drawer.setSidePane(anchor);
			burgerTask = new HamburgerBasicCloseTransition(hamburger);
			burgerTask.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
				burgerTask.setRate(burgerTask.getRate() * -1);
				burgerTask.play();
				if (drawer.isShown()) {
					drawer.close();
				} else {
					drawer.open();
				}
			});
			if (LoginApp.getInstance().getStage().isMaximized() || LoginApp.getInstance().getStage().isFullScreen()) {
				burgerTask.setRate(burgerTask.getRate() * -1);
				burgerTask.play();
				drawer.open();
			}
		} catch (IOException e) {
			LOGGER.error("Error initializing Home Page's drawer: " + e);
		}
		LOGGER.info("Home Page initialized");
	}

	@FXML
	void processDeleteDay(ActionEvent event) {
		if (rowData != null) {
			try {
				DayServices.deleteDay(rowData);
				JFXSnackbar snack = new JFXSnackbar(stackPane);
				snack.enqueue(new SnackbarEvent("Day deleted".toUpperCase()));
				rowData = null;
				ingredientsMeal.setText("");
				caloriesDay.setText("");
				proteinDay.setText("");
				carbsDay.setText("");
				fatDay.setText("");
				updateTable();
			} catch (Exception e) {
				LOGGER.error("Error deleting day. Exception: " + e);
			}
		}
	}

	@FXML
	void processExportDay(ActionEvent event) {

	}

}
