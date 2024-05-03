package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import model.Activity;
import model.DataModel;
import model.Employee;
import model.Project;

import java.io.IOException;
import java.util.List;

public class RegisterTimeController extends StandardController {

    @FXML
    private GridPane workGrid;
    @FXML
    private GridPane personalGrid;
    @FXML
    private ComboBox<String> activitySelector;
    @FXML
    private ToggleButton workButton;
    @FXML
    private ToggleButton personalButton;


    public void initController(DataModel model, ProPlannerPlus proPlannerPlus) {
        super.initController(model, proPlannerPlus);

        List<String> activityNames = proPlannerPlus.getActivities().stream().map(Activity::getName).toList();
        ObservableList<String> projects = FXCollections.observableList(activityNames);
        activitySelector.setItems(projects);
        personalGrid.setManaged(false);
        personalGrid.setVisible(false);
    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void toggleButtonsWork(){
        if (!workButton.isSelected()){
            workButton.setSelected(true);
        } else  {
            changeActiveGrid();
        }
        personalButton.setSelected(false);
    }

    @FXML
    private void toggleButtonsPersonal(){
        if (!personalButton.isSelected()){
            personalButton.setSelected(true);
        } else {
            changeActiveGrid();
        }
        workButton.setSelected(false);
    }

    private void changeActiveGrid() {
        personalGrid.setManaged(!personalGrid.isManaged());
        personalGrid.setVisible(!personalGrid.isVisible());
        workGrid.setManaged(!workGrid.isManaged());
        workGrid.setVisible(!workGrid.isVisible());
    }



}