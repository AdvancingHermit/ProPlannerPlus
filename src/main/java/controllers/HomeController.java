package controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import model.DataModel;

public class HomeController extends StandardController {


    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
    @FXML
    private void switchToCreateProject() throws IOException {
        App.setRoot("createProject");
    }
    @FXML
    private void switchToViewProjects() throws IOException {
        App.setRoot("viewProjects");
    }
    @FXML
    private void switchToManageEmployees() throws IOException {
        App.setRoot("manageEmployees");
    }
    @FXML
    private void switchToCreateActivity() throws IOException {
        App.setRoot("createActivity");
    }

}