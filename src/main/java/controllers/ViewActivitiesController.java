package controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewActivitiesController extends StandardController {

    @FXML
    GridPane activityDetails, employeeDetails;
    @FXML
    ToggleButton activityDetailsButton, employeeDetailsButton, completedToggle;
    @FXML
    ComboBox<Activity> activitySelector;
    @FXML
    TableView<FreeEmployee> employeeListSelector, currentEmployeeSelector;
    @FXML
    private TextField activityNameField, activityHourField;
    @FXML
    private DatePicker activityStartDate, activityEndDate;
    @FXML
    private Label errorText;

    ObservableList<FreeEmployee> freeEmployees;
    ObservableList<FreeEmployee> addedEmployees;


    //Made by Oscar and Oliver
    public void initController(EmployeeData employeeData, ProjectData projectData, ProPlannerPlus proPlannerPlus) {
        super.initController(employeeData, projectData,proPlannerPlus);
        setActivityDetails(false);
        List<Integer> activitieIDs = projectData.getCurrProject().getActivityIDs();
        List<Activity> activityList = activitieIDs.stream().map(a -> proPlannerPlus.getActivity(a)).toList();
        ObservableList<Activity> activities = FXCollections.observableList(activityList);
        errorText.setVisible(false);

        activitySelector.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                updateEmployeeList();
                fillActivityModifyForm();

            } catch (OperationNotAllowedException e) {
                throw new RuntimeException(e);
            }
        });

        employeeListSelector.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        currentEmployeeSelector.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        activitySelector.setItems(activities);
        employeeListSelector.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        currentEmployeeSelector.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        createEmployeeList();
        setEmployeeDetails(false);

        activityHourField.setTextFormatter(doubleTextFormatter);
        activityHourField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal && activityHourField.getText().isEmpty()) {
                activityHourField.setText("0");
            }
        });

    }

    @FXML
    private void activityChosen() throws OperationNotAllowedException {
        updateEmployeeList();
    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    //Made by Oscar
    @FXML
    private void viewActivityDetails() {
        if(activitySelector.getValue() != null){
            if (!activityDetailsButton.isSelected()){
                activityDetailsButton.setSelected(true);
            }
            setActivityDetails(true);
            setEmployeeDetails(false);
            fillActivityModifyForm();
        } else {
            activityDetailsButton.setSelected(false);
        }
        employeeDetailsButton.setSelected(false);
    }
    //Made by Oscar
    @FXML
    private void viewEmployeeDetails() throws OperationNotAllowedException {
        if(activitySelector.getValue() != null){
            if (!employeeDetailsButton.isSelected()){
                employeeDetailsButton.setSelected(true);
            }
            setEmployeeDetails(true);
            setActivityDetails(false);
            updateEmployeeList();
        } else {
            employeeDetailsButton.setSelected(false);
        }
        activityDetailsButton.setSelected(false);
    }


    private void setActivityDetails(boolean visible) {
        activityDetails.setManaged(visible);
        activityDetails.setVisible(visible);
    }

    private void setEmployeeDetails(boolean visible) {
        employeeDetails.setManaged(visible);
        employeeDetails.setVisible(visible);
    }
    @FXML
    public void addEmployeeToActivity() throws OperationNotAllowedException {

        for (FreeEmployee employeeEntry : employeeListSelector.getSelectionModel().getSelectedItems()) {
            activitySelector.getValue().addEmployee(employeeEntry.employee());
        }
        updateEmployeeList();
    }

    @FXML
    public void removeEmployeeFromActivity() throws OperationNotAllowedException {
        for (FreeEmployee employeeEntry : currentEmployeeSelector.getSelectionModel().getSelectedItems()) {
            activitySelector.getValue().removeEmployee(employeeEntry.employee());
        }
        updateEmployeeList();
    }
    //Made by Oscar
    @FXML
    private void changeActivity(){
        Activity activity = activitySelector.getValue();
        LocalDate start = activityStartDate.getValue();
        LocalDate end = activityEndDate.getValue();
        Integer projectID = projectData.getCurrProject().getId();
        String activityName = activityNameField.getText();
        String activityHours = activityHourField.getText();
        boolean completed = completedToggle.isSelected();

        try {
            proPlannerPlus.modifyActivity(activity.getActivityID(), activityName, getNumber(activityHours), start, end, projectID, completed);
            App.setRoot("home");
        } catch (Exception e) {
            errorText.setText(e.getMessage());
            errorText.setVisible(true);
        }
    }
    //Made by Oscar
    private void updateEmployeeList() throws OperationNotAllowedException {
        String projectName = projectData.getCurrProject().getName();
        Activity activity = activitySelector.getValue();
        LocalDate startDate = activity.getStartDate();
        LocalDate endDate = activity.getEndDate();

        freeEmployees = FXCollections.observableList(new ArrayList<>());
        employeeListSelector.setItems(freeEmployees);

        addedEmployees = FXCollections.observableList(new ArrayList<>());

        Map<Employee, Integer> temp = proPlannerPlus.getFreeEmployees(startDate, endDate);

        for (Map.Entry<Employee, Integer> entry : temp.entrySet()) {
            if (!activity.getEmployees().contains(entry.getKey())) {
                freeEmployees.add(new FreeEmployee(entry.getKey(), entry.getValue()));
            } else {
                addedEmployees.add(new FreeEmployee(entry.getKey(), entry.getValue()));
            }

        }
        currentEmployeeSelector.setItems(addedEmployees);
        currentEmployeeSelector.setItems(addedEmployees);
    }
    //Made by Oscar
    @FXML
    private void fillActivityModifyForm() {
        Activity activity = activitySelector.getValue();
        activityNameField.setText(activity.getName());
        activityHourField.setText(activity.getTotalTime() + "");
        activityStartDate.setValue(activity.getStartDate());
        activityEndDate.setValue(activity.getEndDate());
        completedToggle.selectedProperty().set(activity.getComplete());

    }
    //Made by Oscar
    private void createEmployeeList() {

        TableColumn<FreeEmployee, String> currentInitialsColumn = new TableColumn<>("Employee");
        currentInitialsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().employee.getInitials()));

        TableColumn<FreeEmployee, Integer> currentOverlapCountColumn = new TableColumn<>("Overlapping Activities");
        currentOverlapCountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().overlapCount - 1).asObject());
        currentEmployeeSelector.getColumns().addAll(currentInitialsColumn, currentOverlapCountColumn);

        TableColumn<FreeEmployee, String> initialsColumn = new TableColumn<>("Employee");
        initialsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().employee.getInitials()));

        TableColumn<FreeEmployee, Integer> overlapCountColumn = new TableColumn<>("Overlapping Activities");
        overlapCountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().overlapCount).asObject());
        employeeListSelector.getColumns().addAll(initialsColumn, overlapCountColumn);

    }
    //Made by Oscar
    private record FreeEmployee(Employee employee, int overlapCount) {
        @Override
        public String toString() {
            return String.format("Employee: %-20s Overlapping Activities: %s", employee, overlapCount);
        }
    }

}