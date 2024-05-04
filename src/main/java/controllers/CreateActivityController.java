package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
    private Label errorText;

    @FXML
    ComboBox<String> projectSelector;

    public void initController(DataModel model, ProPlannerPlus proPlannerPlus) {
        super.initController(model, proPlannerPlus);
        List<String> projectNames = proPlannerPlus.getProjects().stream().map(Project::getName).toList();
        ObservableList<String> projects = FXCollections.observableList(projectNames);
        projectSelector.setItems(projects);
        createActivityHours.setTextFormatter(doubleTextFormatter);
        createActivityHours.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal && createActivityHours.getText().isEmpty()) {
                createActivityHours.setText("0");
            }
        });
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

        String activityName = createActivityName.getText();
        String activityHours = createActivityHours.getText();

        try {
            proPlannerPlus.createActivity(activityName, getNumber(activityHours), start, end, projectName);
            App.setRoot("home");
        } catch (Exception e) {
            errorText.setText(e.getMessage());
        }


    }

}