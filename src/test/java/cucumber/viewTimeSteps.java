package cucumber;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Activity;
import model.Employee;
import model.Project;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;

public class viewTimeSteps {
    ProPlannerPlus proPlannerPlus;
    public viewTimeSteps() {
        proPlannerPlus = new ProPlannerPlus();
    }
    Employee employee;
    @Given("An employee exist")
    public void anEmployeeExist() {
        employee = new Employee("Test");

    }

    @And("a project exist")
    public void aProjectExist() throws OperationNotAllowedException {
        proPlannerPlus.createProject("TestProject");
        Activity activity1 = new Activity(
                "Hej",50,
                LocalDate.of(2003, 2, 3),
                LocalDate.of(2003, 2, 16), 0);
        proPlannerPlus.createActivity(activity1, "TestProject");
        Activity activity2 = new Activity(
                "Med",25,
                LocalDate.of(2003, 3, 3),
                LocalDate.of(2003, 4, 16), 1);
        proPlannerPlus.createActivity(activity2, "TestProject");
        proPlannerPlus.getActivity(activity1.getActivityID()).setCompletion(true);
    }
    double testRun = 0;
    @When("The employee views time status for the project")
    public void theEmployeeViewsTimeStatusForTheProject() {
         testRun = proPlannerPlus.getCompletionsStatus(proPlannerPlus.getProject("TestProject"));
    }

    @Then("The current status of time for the project is shown")
    public void theCurrentStatusOfTimeForTheProjectIsShown() {
        assertTrue(proPlannerPlus.getCompletionsStatus(proPlannerPlus.getProject("TestProject")) == testRun);
    }
}
