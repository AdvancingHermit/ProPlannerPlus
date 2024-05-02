package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ManageEmployeeController extends StandardController {
    @FXML
    private TextField employeeText;

    @FXML
    private Label errorText;

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void addRemoveEmployee() throws IOException {
    }
}