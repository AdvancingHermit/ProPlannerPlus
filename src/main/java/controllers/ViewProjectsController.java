package controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Activity;
import model.DataModel;
import model.Employee;
import model.Project;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class ViewProjectsController extends StandardController {

    @FXML
    GridPane projectDetails;

    @FXML
    ComboBox<Project> projectSelector;

    @FXML
    ComboBox<Employee> employeesSelector;

    @FXML
    Label errorText;

    @FXML
    Label completionText;

    public void initController(DataModel model, ProPlannerPlus proPlannerPlus) {
        super.initController(model, proPlannerPlus);
        setProjectDetails(false);
        ObservableList<Employee> employees = FXCollections.observableList( proPlannerPlus.getEmployees() );
        ObservableList<Project> projects = FXCollections.observableList( proPlannerPlus.getProjects() );
        errorText.setVisible(false);
        employeesSelector.setItems(employees);
        projectSelector.setItems(projects);
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

            projectSelector.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {

                    if (projectSelector.getValue().getProjectLeader() != null) {
                        employeesSelector.getSelectionModel().select(projectSelector.getValue().getProjectLeader());
                    }

                    else {
                        employeesSelector.getSelectionModel().select(null);
                    }
                }
            });

        }
        else{
            errorText.setVisible(true);
            errorText.setText("No Project Selected");
        }
    }



    private void setProjectDetails(boolean visible) {
        projectDetails.setManaged(visible);
        projectDetails.setVisible(visible);
    }


    @FXML
    private void setProjectLeader(){
        projectSelector.getValue().setProjectLeader(employeesSelector.getValue());
    }

    @FXML
    public void showCompletionStatus() throws IOException {
        model.setCurrProject(projectSelector.getValue());
        App.setRoot("completionStatus");
    }

    @FXML
    private void viewActivityDetails() throws IOException {
        model.setCurrProject(projectSelector.getValue());
        App.setRoot("viewActivities");
    }


}