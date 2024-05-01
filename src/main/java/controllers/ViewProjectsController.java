package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import model.DataModel;
import model.Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewProjectsController extends StandardController {

    @FXML
    GridPane projectDetails;

    @FXML
    ComboBox<String> projectSelector;

    public void initController(DataModel model, ProPlannerPlus proPlannerPlus) {
        super.initController(model, proPlannerPlus);
        setProjectDetails(false);
        List<String> projectNames = proPlannerPlus.getProjects().stream().map(Project::getName).toList();
        ObservableList<String> projects = FXCollections.observableList(projectNames);
        projectSelector.setItems(projects);

    }


    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void viewProjectDetails() throws IOException {
        setProjectDetails(true);
    }

    private void setProjectDetails(boolean visible) {
        projectDetails.setManaged(visible);
        projectDetails.setVisible(visible);
    }

}