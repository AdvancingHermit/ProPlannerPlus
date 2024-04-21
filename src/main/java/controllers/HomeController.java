package controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import model.DataModel;

public class HomeController extends StandardController {


    @FXML
    private void switchToLogin() throws IOException {
        System.out.println(model.getCurrentEmployee().getInitials());
        App.setRoot("login");
    }
}