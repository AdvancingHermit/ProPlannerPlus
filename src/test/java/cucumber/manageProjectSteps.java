package cucumber;

import model.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Activity;
import model.Employee;
import model.Project;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//Made by Christian
public class manageProjectSteps {
    ProPlannerPlus proPlannerPlus;
    Employee employee;
    Project project = new Project("testP",1);

    double status;

    private ErrorMessageHolder errorMessageHolder;
    public manageProjectSteps(ErrorMessageHolder errorMessageHolder) {
        proPlannerPlus = new ProPlannerPlus();
        this.errorMessageHolder = errorMessageHolder;
    }
    @Given("an employee exist")
    public void anEmployeeExist() throws OperationNotAllowedException {
        employee = new Employee("test");
        proPlannerPlus.addEmployee(employee);
    }

    @Given("a project with no activities")
    public void aProjectWithNoActivities() {
        proPlannerPlus.createTestProject(project);
    }
    @Given("a project contains {int} activites")
    public void aProjectContainsActivites(Integer int1) {

        proPlannerPlus.createTestProject(project);

        Activity activity;

        for (int i = 0; i < int1; i++){
            activity = new Activity("testAct", 2,
                    LocalDate.of(2024, 05, 03),
                    LocalDate.of(2024, 06, 03),
                    i);

            proPlannerPlus.createActivity(activity, project.getId());
        }
    }
    @Given("an activity with {int} hours is completed is in the project")
    public void anActivityWithHoursIsCompletedIsInTheProject(Integer int1) throws OperationNotAllowedException {
        proPlannerPlus.registerTime(0, employee.getInitials(), int1);
        proPlannerPlus.getActivity(0).setCompletion(true);
    }
    @Given("an uncompleted activity with {int} hours expected time is in the project")
    public void anUncompletedActivityWithHoursExpectedTimeIsInTheProject(Integer int1) throws OperationNotAllowedException {
        proPlannerPlus.getActivity(0).setTotalTime(int1);
        proPlannerPlus.getActivity(0).setCompletion(false);
    }
    @Given("an activity with id {int} and {int} hours is completed is in the project")
    public void anActivityWithIdAndHoursIsCompletedIsInTheProject(Integer int1, Integer int2) throws OperationNotAllowedException {
        proPlannerPlus.registerTime(int1, employee.getInitials(), int2);
        proPlannerPlus.getActivity(int1).setCompletion(true);
    }
    @Given("an uncompleted activity with id {int} and {int} hours expected time is in the project")
    public void anUncompletedActivityWithIdAndHoursExpectedTimeIsInTheProject(Integer int1, Integer int2) throws OperationNotAllowedException {
        proPlannerPlus.getActivity(int1).setTotalTime(int2);
        proPlannerPlus.getActivity(int1).setCompletion(false);
    }

    @When("the employee views completion status")
    public void theEmployeeViewsCompletionStatus() {
        try {
            status = proPlannerPlus.getCompletionStatus(proPlannerPlus.getProject(project.getId()));
        }catch (AssertionError | OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the status is {double}")
    public void theStatusIs(Double double1) {
        assertEquals(double1, status, 0.0);
    }
}
