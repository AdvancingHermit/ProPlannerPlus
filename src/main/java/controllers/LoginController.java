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
        System.out.println(model);
        String initials = initialsField.getText();
        if (ProPlannerPlus.login(initials)) {
            model.setCurrentEmployee(new Employee(initials));
            App.setRoot("home");
        } else {

            errorText.setText("Please enter valid initials!");
        }


    }



}
