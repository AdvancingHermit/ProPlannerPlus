package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Project;
import java.io.IOException;

public class CreateProjectController extends StandardController {

    @FXML
    private TextField createProject;

    @FXML
    private Label errorText;


    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void createProject() throws IOException {
        if (!createProject.getText().isEmpty()){
            proPlannerPlus.createProject(createProject.getText());
            switchToHome();
        } else{
            errorText.setText("Please enter a name");
        }

    }
}