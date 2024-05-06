package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.DataModel;
import model.Employee;
import model.Project;

import java.io.IOException;

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

    //Made by Oscar
    public void initController(DataModel model, ProPlannerPlus proPlannerPlus) {
        super.initController(model, proPlannerPlus);
        setProjectDetails(false);
        ObservableList<Employee> employees = FXCollections.observableList( proPlannerPlus.getEmployees() );
        ObservableList<Project> projects = FXCollections.observableList( proPlannerPlus.getProjects() );
        setErrorText(false);

        employeesSelector.setItems(employees);
        projectSelector.setItems(projects);
    }


    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    //Made by Christian
    @FXML
    private void viewProjectDetails() throws IOException {
        if (projectSelector.getValue() != null) {
            setErrorText(false);
            setProjectDetails(true);

            employeesSelector.getSelectionModel().select(projectSelector.getValue().getProjectLeader());

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
            setErrorText(true);
            errorText.setText("No Project Selected");
        }
    }



    private void setProjectDetails(boolean visible) {
        projectDetails.setManaged(visible);
        projectDetails.setVisible(visible);
    }

    private void setErrorText(boolean visible) {
        errorText.setManaged(visible);
        errorText.setVisible(visible);
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