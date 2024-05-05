package cucumber;

import controllers.OperationNotAllowedException;
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

public class manageProjectSteps {
    ProPlannerPlus proPlannerPlus;
    Employee employee;
    String project = new String("testP");

    double status;
    public manageProjectSteps() {
        proPlannerPlus = new ProPlannerPlus();
    }
    @Given("an employee exist")
    public void anEmployeeExist() throws OperationNotAllowedException {
        employee = new Employee("testE");
        proPlannerPlus.addEmployee(employee);
    }

    @Given("a project with no activities")
    public void aProjectWithNoActivities() {
        proPlannerPlus.createProject(project);
    }
    @Given("a project contains {int} activites")
    public void aProjectContainsActivites(Integer int1) {

        proPlannerPlus.createProject(project);

        Activity activity;

        for (int i = 0; i < int1; i++){
            activity = new Activity("testAct", 2,
                    LocalDate.of(2024, 05, 03),
                    LocalDate.of(2024, 06, 03),
                    i);

            proPlannerPlus.createActivity(activity, project);
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
        status = proPlannerPlus.getCompletionStatus(proPlannerPlus.getProject(project));
    }

    @Then("the status is {double}")
    public void theStatusIs(Double double1) {
        assertEquals(double1, status, 0.0);
    }
}
