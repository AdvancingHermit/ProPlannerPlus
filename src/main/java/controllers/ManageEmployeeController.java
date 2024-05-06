package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import model.DataModel;
import model.Employee;

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
    private void switchToAway() throws IOException {
        if (!proPlannerPlus.adminLoggedIn) {
            App.setRoot("home");
        } else {
            logout();
        }
    }

    private void logout() throws IOException {
        proPlannerPlus.loggedIn = false;
        proPlannerPlus.adminLoggedIn = false;
        App.setRoot("login");
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
        if (employeeText.getText().length() > 4){
            errorText.setText("Initials can only be up to 4 letters");
            return;
        }
        if (employeeText.getText().isEmpty()){
            errorText.setText("Please enter a name");
            return;
        }

        if (addButton.isSelected()){
                List<String> employeeInitials = ProPlannerPlus.getEmployees().stream().map(Employee::getInitials).toList();
                if (!employeeInitials.contains(employeeText.getText())) {
                    ProPlannerPlus.addEmployee(new Employee(employeeText.getText()));
                    switchToAway();
                } else {
                    errorText.setText("Employee already exist");
                }
            }

        if (removeButton.isSelected()) {
                List<String> employeeInitials = ProPlannerPlus.getEmployees().stream().map(Employee::getInitials).toList();
                if (employeeText.getText().equals(model.getCurrentEmployee().getInitials())){
                    ProPlannerPlus.removeEmployee(employeeText.getText());
                    logout();
                }
                else if (employeeInitials.contains(employeeText.getText())) {
                    ProPlannerPlus.removeEmployee(employeeText.getText());
                    switchToAway();
                } else {
                    errorText.setText("The employee doesn't exist");
                }
            }
    }




}