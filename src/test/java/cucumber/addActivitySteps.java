package cucumber;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Activity;
import model.Employee;
import model.Project;
import org.junit.Assert;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

//This test is mady by Oliver
public class addActivitySteps {

    ProPlannerPlus proPlannerPlus;

    Project project;

    Employee employee;

    Activity activity;

    public addActivitySteps(){ proPlannerPlus = new ProPlannerPlus(); }

    @Given("an Employee a project, and an activity")
    public void an_employee_a_project_and_an_activity() {
        project = new Project("Test", 1);
        activity = new Activity("testAct", 10,LocalDate.of(2024, 05, 03),
                LocalDate.of(2024, 06, 03), 1);

    }
    @When("the employee adds the activity to the project")
    public void the_employee_adds_the_activity_to_the_project() {
        ProPlannerPlus.addActivityToProject(project,activity.getActivityID());
    }
    @Then("the activity is now under the project")
    public void the_activity_is_now_under_the_project() {
        assertTrue(project.getActivityIDs().contains(activity.getActivityID()));
    }



}
