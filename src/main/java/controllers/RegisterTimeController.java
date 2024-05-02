package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import model.DataModel;
import model.Employee;
import model.Project;

import java.io.IOException;
import java.util.List;

public class RegisterTimeController extends StandardController {

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

}