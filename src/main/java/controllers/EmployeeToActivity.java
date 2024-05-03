package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Activity;
import model.DataModel;
import model.Employee;
import model.Project;

import java.io.IOException;
import java.util.List;

public class EmployeeToActivity extends StandardController{

    @FXML
    ComboBox<Activity> activitySelector;
    @FXML
    ComboBox<Employee> employeesSelector;
    @FXML
    private TextField timeUsed;

    @Override
    public void initController(DataModel model, ProPlannerPlus proPlannerPlus) {
        super.initController(model, proPlannerPlus);
        ObservableList<Employee> employees = FXCollections.observableList(proPlannerPlus.getEmployees());
        ObservableList<Activity> activities = FXCollections.observableList(proPlannerPlus.getActivities());
        activitySelector.setItems(activities);
        employeesSelector.setItems(employees);
    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
    @FXML
    private void setEmployeeToActivity() throws IOException{
        activitySelector.getValue().addEmployee(employeesSelector.getValue());
    }



}
