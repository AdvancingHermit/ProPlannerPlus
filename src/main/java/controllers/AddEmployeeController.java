package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Employee;

import java.io.IOException;
import java.util.List;

public class AddEmployeeController extends StandardController {
    @FXML
    private TextField employeeText;

    @FXML
    private Label errorText;

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void addEmployee() throws IOException, OperationNotAllowedException {
        if (!employeeText.getText().isEmpty()){
            ProPlannerPlus.addEmployee(new Employee(employeeText.getText()));
            switchToLogin();
        } else{
            errorText.setText("Please enter a name");
        }
    }
    private void removeEmployee() throws IOException, OperationNotAllowedException {
        if (!employeeText.getText().isEmpty()){
            List<String> employeeInitials = ProPlannerPlus.getEmployees().stream().map(Employee::getInitials).toList();
            if (employeeInitials.contains(employeeText.getText())) {
                ProPlannerPlus.removeEmployee(employeeText.getText());
                switchToLogin();
            }else {
                errorText.setText("The employee doesn't exist");
            }
        } else{
            errorText.setText("Please enter a name");
        }
    }

}
