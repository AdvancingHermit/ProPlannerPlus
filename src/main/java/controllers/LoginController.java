package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import model.Employee;
import model.DataModel;



public class LoginController extends StandardController {
    Employee currentEmployee;

    @FXML
    private TextField initialsField;
    @FXML
    private Label errorText;


    @FXML
    private void switchToHome() throws IOException {
        String initials = initialsField.getText();
        ProPlannerPlus.login(initials);
        if (proPlannerPlus.loggedIn) {
            model.setCurrentEmployee(proPlannerPlus.getEmployee(initials));
            App.setRoot("home");
        } else if (proPlannerPlus.adminLoggedIn) {
            App.setRoot("manageEmployees");
        } else  {
            errorText.setText("Please enter valid initials!");
        }


    }


}
