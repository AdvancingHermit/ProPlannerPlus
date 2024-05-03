package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Activity;
import model.DataModel;
import model.Employee;
import model.Project;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CreateActivityController extends StandardController {

    @FXML
    private TextField createActivityName;
    @FXML
    private TextField createActivityHours;
    @FXML
    private DatePicker createActivityStart;
    @FXML
    private DatePicker createActivityEnd;

    @FXML
    ComboBox<String> projectSelector;

    public void initController(DataModel model, ProPlannerPlus proPlannerPlus) {
        super.initController(model, proPlannerPlus);
        List<String> projectNames = proPlannerPlus.getProjects().stream().map(Project::getName).toList();
        ObservableList<String> projects = FXCollections.observableList(projectNames);
        projectSelector.setItems(projects);
    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void createActivity() throws IOException {
        LocalDate start = createActivityStart.getValue();
        LocalDate end = createActivityEnd.getValue();
        String projectName = projectSelector.getValue();

        if (!createActivityName.getText().isEmpty() || createActivityHours.getText().isEmpty() || start == null || end == null){

            if (getNumber(createActivityHours.getText()) != -1) {
                proPlannerPlus.createActivity(createActivityName.getText(), Float.parseFloat(createActivityHours.getText()), start, end, projectName);

            }

        }
        for (Activity activity : proPlannerPlus.getActivities()) {
            System.out.println(activity.getName());
        }

    }

}