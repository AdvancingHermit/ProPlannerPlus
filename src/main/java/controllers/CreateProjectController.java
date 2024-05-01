package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Project;
import java.io.IOException;

public class CreateProjectController extends StandardController {

    @FXML
    private TextField createProject;


    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void createProject() throws IOException {
        proPlannerPlus.createProject(createProject.getText());
        for (Project project : proPlannerPlus.getProjects()) {
            System.out.println(project.getName());
        }
        switchToHome();
    }
}