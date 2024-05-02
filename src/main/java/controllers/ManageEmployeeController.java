package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import model.DataModel;
import model.Employee;
import model.Project;

import java.io.IOException;
import java.util.List;

public class ManageEmployeeController extends StandardController {
    @FXML
    private TextField employeeText;

    @FXML
    private Label errorText;

    @FXML
    private ToggleButton removeButton;

    @FXML
    private ToggleButton addButton;


    public void initController(DataModel model, ProPlannerPlus proPlannerPlus) {
        super.initController(model, proPlannerPlus);
        addButton.setSelected(true);
    }




    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void toggleButtonsRemove(){
        if (!removeButton.isSelected()){
            removeButton.setSelected(true);
        }
         addButton.setSelected(false);
    }

    @FXML
    private void toggleButtonsAdd(){
        if (!addButton.isSelected()){
            addButton.setSelected(true);
        }
        removeButton.setSelected(false);
    }



    @FXML
    private void addRemoveEmployee() throws IOException, OperationNotAllowedException {
        if (addButton.isSelected()){
            if (!employeeText.getText().isEmpty()){
                List<String> employeeInitials = ProPlannerPlus.getEmployees().stream().map(Employee::getInitials).toList();
                if (!employeeInitials.contains(employeeText.getText())) {
                    ProPlannerPlus.addEmployee(new Employee(employeeText.getText()));
                    switchToHome();
                } else {
                    errorText.setText("Employee already exist");
                }
            } else{
                errorText.setText("Please enter a name");
            }
        }
        if (removeButton.isSelected()) {
            if (!employeeText.getText().isEmpty()) {
                List<String> employeeInitials = ProPlannerPlus.getEmployees().stream().map(Employee::getInitials).toList();
                if (employeeInitials.contains(employeeText.getText())) {
                    ProPlannerPlus.removeEmployee(employeeText.getText());
                    switchToHome();
                } else {
                    errorText.setText("The employee doesn't exist");
                }
            } else {
                errorText.setText("Please enter a name");
            }
        }



    }




}