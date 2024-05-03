package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;
import model.Activity;
import model.DataModel;
import model.Employee;
import model.Project;

import java.io.IOException;
import java.util.List;
import java.util.function.UnaryOperator;

public class RegisterTimeController extends StandardController {

    @FXML
    private GridPane workGrid;
    @FXML
    private GridPane personalGrid;
    @FXML
    private ComboBox<Activity> activitySelector;
    @FXML
    private ToggleButton workButton;
    @FXML
    private ToggleButton personalButton;
    @FXML
    private TextField hourInput;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Label errorText;
    @FXML
    private TextField reasonText;
    Employee currentEmployee;


    public void initController(DataModel model, ProPlannerPlus proPlannerPlus) {
        super.initController(model, proPlannerPlus);
        ObservableList<Activity> activities = FXCollections.observableList(proPlannerPlus.getActivities());
        activitySelector.setItems(activities);
        personalGrid.setManaged(false);
        personalGrid.setVisible(false);
        currentEmployee = model.getCurrentEmployee();
        hourInput.setTextFormatter(doubleTextFormatter);
        hourInput.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal && hourInput.getText().isEmpty()) {
                hourInput.setText("0");
            }
        });
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
        errorText.setText("");
    }


    @FXML
    private void registerTime() throws IOException {
        if (workButton.isSelected()){
            String error = "";
            Activity activity = activitySelector.getValue();
            String hoursText = hourInput.getText();
            if  (activity == null) {
                error = "Please select a valid activity";
            } else  {
            currentEmployee.registerTime(activity.getActivityID(), Double.parseDouble(hoursText));
            App.setRoot("home");
            }
            errorText.setText(error);
        } else {
            try {
                currentEmployee.addPersonalActivity(startDatePicker.getValue(), endDatePicker.getValue(), reasonText.getText());
                App.setRoot("home");
            } catch (Exception e) {
                errorText.setText(e.getMessage());
            }
        }

    }



}