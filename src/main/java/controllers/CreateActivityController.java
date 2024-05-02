package controllers;

import javafx.fxml.FXML;

import java.io.IOException;

public class CreateActivityController extends StandardController {


    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void createActivity() throws IOException {
    }

}