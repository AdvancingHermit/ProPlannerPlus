package controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Activity;
import model.DataModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class showCompletionStatusController extends StandardController {
    @FXML
    private Label completionAmount;

    @FXML
    private TableView<Activity> statusOfActivities;

    private ObservableList<Activity> activities = FXCollections.observableArrayList();

    @Override
    public void initController(DataModel model, ProPlannerPlus proPlannerPlus) {
        super.initController(model, proPlannerPlus);

        for (int id : model.getCurrProject().getActivityIDs()) {
            activities.add(proPlannerPlus.getActivity(id));
        }

        statusOfActivities.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        statusOfActivities.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        completionAmount.setText( String.valueOf(proPlannerPlus.getCompletionsStatus(model.getCurrProject())) + "%");

        createActivityList();
    }

    public void switchToHome() throws IOException {
        App.setRoot("home");
    }

    private void createActivityList() {
        statusOfActivities.setItems(activities);

        TableColumn<Activity, String> activityNamesColumn = new TableColumn<>("Activity");
        activityNamesColumn.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getName()) );

        TableColumn<Activity, Double> expectedTimeColumn = new TableColumn<>("Expected Time");
        expectedTimeColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty( cellData.getValue().getTotalTime()).asObject() );

        TableColumn<Activity, Double> actualTimeColumn = new TableColumn<>("Actual Time");
        actualTimeColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleDoubleProperty( proPlannerPlus.actualTimeSpentOnActivity(cellData.getValue().getActivityID())).asObject();
            } catch (OperationNotAllowedException e) {
                throw new RuntimeException(e);
            }
        });

        TableColumn<Activity, Boolean> completionStatusColumn = new TableColumn<>("Is completed");
        completionStatusColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty( cellData.getValue().getComplete()) );

        statusOfActivities.getColumns().addAll(activityNamesColumn, expectedTimeColumn, actualTimeColumn, completionStatusColumn);
    }
}