package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Activity;
import model.DataModel;
import model.Employee;
import model.Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewProjectsController extends StandardController {

    @FXML
    GridPane projectDetails;

    @FXML
    GridPane activityDetails;

    @FXML
    ComboBox<Project> projectSelector;

    @FXML
    ComboBox<Employee> employeesSelector;

    @FXML
    ComboBox<Activity> activitySelector;

    @FXML
    ComboBox<Employee> employeeActivitySelector;

    @FXML
    Label errorText;

    @FXML
    Label completionText;

    public void initController(DataModel model, ProPlannerPlus proPlannerPlus) {
        super.initController(model, proPlannerPlus);
        setProjectDetails(false);
        setActivityDetails(false);
        ObservableList<Employee> employees = FXCollections.observableList( proPlannerPlus.getEmployees() );
        ObservableList<Project> projects = FXCollections.observableList( proPlannerPlus.getProjects() );
        ObservableList<Activity> activities = FXCollections.observableList( proPlannerPlus.getActivities() );
        errorText.setVisible(false);

        employeesSelector.setItems(employees);
        projectSelector.setItems(projects);
        activitySelector.setItems(activities);
        employeeActivitySelector.setItems(employees);
    }


    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void viewProjectDetails() throws IOException {
        if (projectSelector.getValue() != null) {
            errorText.setVisible(false);
            setProjectDetails(true);
            Employee projectLeader = projectSelector.getValue().getProjectLeader();
            System.out.println(projectLeader);
            if (projectLeader != null) {
                employeesSelector.getSelectionModel().select(projectLeader);
            }
        }
        else{
            errorText.setVisible(true);
            errorText.setText("No Project Selected");
        }
    }

    @FXML
    private void viewActivityDetails() throws IOException {
        setActivityDetails(true);
    }

    private void setProjectDetails(boolean visible) {
        projectDetails.setManaged(visible);
        projectDetails.setVisible(visible);
    }

    private void setActivityDetails(boolean visible) {
        activityDetails.setManaged(visible);
        activityDetails.setVisible(visible);
    }

    @FXML
    private void setProjectLeader(){
        projectSelector.getValue().setProjectLeader(employeesSelector.getValue());
    }

    public void addEmployeeToActivity() {
        activitySelector.getValue().addEmployee(employeeActivitySelector.getValue());
    }

    public void showCompletionStatus() {
        if (projectSelector.getValue() != null){
            completionText.setText( String.valueOf(proPlannerPlus.getCompletionsStatus(projectSelector.getValue())) + "%");
        }
    }
}