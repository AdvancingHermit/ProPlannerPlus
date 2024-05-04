package controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Activity;
import model.DataModel;
import model.Employee;
import model.Project;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class ViewProjectsController extends StandardController {

    @FXML
    GridPane projectDetails;

    @FXML
    GridPane activityDetails;

    @FXML
    ComboBox<Project> projectSelector;

    @FXML
    ComboBox<Employee> employeesSelector;

    @FXML
    ComboBox<Activity> activitySelector;

    @FXML
    TableView<FreeEmployee> employeeListSelector;

    @FXML
    TableView<FreeEmployee> currentEmployeeSelector;

    ObservableList<FreeEmployee> freeEmployees;
    ObservableList<FreeEmployee> addedEmployees;


    @FXML
    Label errorText;

    @FXML
    Label completionText;

    public void initController(DataModel model, ProPlannerPlus proPlannerPlus) {
        super.initController(model, proPlannerPlus);
        setProjectDetails(false);
        setActivityDetails(false);
        ObservableList<Employee> employees = FXCollections.observableList( proPlannerPlus.getEmployees() );
        ObservableList<Project> projects = FXCollections.observableList( proPlannerPlus.getProjects() );
        ObservableList<Activity> activities = FXCollections.observableList( proPlannerPlus.getActivities() );
        errorText.setVisible(false);

        employeeListSelector.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        currentEmployeeSelector.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        employeesSelector.setItems(employees);
        projectSelector.setItems(projects);
        activitySelector.setItems(activities);
        activitySelector.setOnAction(e -> {
            setActivityDetails(false);
        });
        employeeListSelector.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        currentEmployeeSelector.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        createEmployeeList();

    }


    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void viewProjectDetails() throws IOException {
        if (projectSelector.getValue() != null) {
            errorText.setVisible(false);
            setProjectDetails(true);
            Employee projectLeader = projectSelector.getValue().getProjectLeader();
            if (projectLeader != null) {
                employeesSelector.getSelectionModel().select(projectLeader);
            }
        }
        else{
            errorText.setVisible(true);
            errorText.setText("No Project Selected");
        }
    }


    @FXML
    private void viewActivityDetails() throws IOException, OperationNotAllowedException {
        if(activitySelector.getValue() != null){
            setActivityDetails(true);
            updateEmployeeList();
        }
    }

    private void setProjectDetails(boolean visible) {
        projectDetails.setManaged(visible);
        projectDetails.setVisible(visible);
    }

    private void setActivityDetails(boolean visible) {
        activityDetails.setManaged(visible);
        activityDetails.setVisible(visible);
    }

    @FXML
    private void setProjectLeader(){
        projectSelector.getValue().setProjectLeader(employeesSelector.getValue());
    }

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

    private void updateEmployeeList() throws OperationNotAllowedException {
        Project project = projectSelector.getValue();
        Activity activity = activitySelector.getValue();
        LocalDate startDate = activity.getStartDate();
        LocalDate endDate = activity.getEndDate();

        freeEmployees = FXCollections.observableList(new ArrayList<>());
        employeeListSelector.setItems(freeEmployees);

        addedEmployees = FXCollections.observableList(new ArrayList<>());
        currentEmployeeSelector.setItems(addedEmployees);

        Map<Employee, Integer> temp = proPlannerPlus.getFreeEmployees(project, startDate, endDate);

        for (Map.Entry<Employee, Integer> entry : temp.entrySet()) {
            if (!activity.getEmployees().contains(entry.getKey())) {
                freeEmployees.add(new FreeEmployee(entry.getKey(), entry.getValue()));
            } else {
                addedEmployees.add(new FreeEmployee(entry.getKey(), entry.getValue()));
            }

        }
    }

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
    @FXML
    public void showCompletionStatus() throws IOException {
        model.setCurrProject(projectSelector.getValue());
        App.setRoot("completionStatus");
    }

    private record FreeEmployee(Employee employee, int overlapCount) {
        @Override
        public String toString() {
            return String.format("Employee: %-20s Overlapping Activities: %s", employee, overlapCount);
        }
    }

}