package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.*;

import java.io.IOException;
import java.util.List;


// Class made by Oscar
public class RegisterTimeController extends StandardController {

    @FXML
    private GridPane workGrid;
    @FXML
    private GridPane projectGrid;
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
    @FXML
    private ComboBox<Project> projectSelector;
    Employee currentEmployee;



    public void initController(EmployeeData employeeData, ProjectData projectData, ProPlannerPlus proPlannerPlus) {
        super.initController(employeeData, projectData,proPlannerPlus);
        ObservableList<Activity> activities = FXCollections.observableList(proPlannerPlus.getActivities());
        activitySelector.setItems(activities);
        ObservableList<Project> projects = FXCollections.observableList(proPlannerPlus.getProjects());
        projectSelector.setItems(projects);
        personalGrid.setManaged(false);
        personalGrid.setVisible(false);
        workGrid.setManaged(false);
        workGrid.setVisible(false);
        projectSelector.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && workButton.isSelected()) {
                workGrid.setManaged(true);
                workGrid.setVisible(true);
                List<Integer> activitieIDs = newValue.getActivityIDs();
                List<Activity> activityList = activitieIDs.stream().map(a -> proPlannerPlus.getActivity(a)).toList();
                ObservableList<Activity>  newActivityList = FXCollections.observableList(activityList);
                activitySelector.setItems(newActivityList);
            }


        });
        currentEmployee = employeeData.getCurrentEmployee();
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
        projectGrid.setManaged(!projectGrid.isManaged());
        projectGrid.setVisible(!projectGrid.isVisible());
        if (projectSelector.getValue() != null) {
            workGrid.setManaged(!workGrid.isManaged());
            workGrid.setVisible(!workGrid.isVisible());
        }
        errorText.setText("");
    }


    @FXML
    private void registerTime() throws IOException, OperationNotAllowedException {
        if (workButton.isSelected()){
            String error = "";
            Activity activity = activitySelector.getValue();
            String hoursText = hourInput.getText();
            if  (activity == null) {
                error = "Please select a valid activity";
            } else  {
                proPlannerPlus.registerTime(activity.getActivityID(), currentEmployee.getInitials(), Double.parseDouble(hoursText));
                App.setRoot("home");
            }
            errorText.setText(error);
        } else {
            try {
                proPlannerPlus.addPersonalActivity(currentEmployee.getInitials(), startDatePicker.getValue(), endDatePicker.getValue(), reasonText.getText());
                App.setRoot("home");
            } catch (Exception e) {
                errorText.setText(e.getMessage());
            }
        }

    }

}