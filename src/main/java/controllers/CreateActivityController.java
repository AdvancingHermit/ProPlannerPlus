package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Activity;
import model.Project;

import java.io.IOException;
import java.time.LocalDate;

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
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void createActivity() throws IOException {
        LocalDate start = createActivityStart.getValue();
        LocalDate end = createActivityEnd.getValue();


        if (!createActivityName.getText().isEmpty() || createActivityHours.getText().isEmpty() || start == null || end == null){

            if (getNumber(createActivityHours.getText()) != -1) {
                proPlannerPlus.createActivity(createActivityName.getText(), Float.parseFloat(createActivityHours.getText()), start, end);
            }

        }
        for (Activity activity : proPlannerPlus.getActivities()) {
            System.out.println(activity.getName());
        }

    }

}