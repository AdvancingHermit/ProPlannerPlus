package cucumber;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Activity;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class activitySteps {

    ProPlannerPlus proPlannerPlus;
    String activityName;
    int hoursPerWeek;
    LocalDate startDate;
    LocalDate endDate;
    String projectName;

    private ErrorMessageHolder errorMessageHolder;
    public activitySteps(ErrorMessageHolder errorMessageHolder) {
        proPlannerPlus = new ProPlannerPlus();
        this.errorMessageHolder = errorMessageHolder;
    }


    @Given("the info of an activity")
    public void the_info_of_an_activity() {
        activityName = "testAct";
        hoursPerWeek = 10;
        startDate = LocalDate.of(2024, 05, 03);
        endDate = LocalDate.of(2024, 06, 03);
        projectName = "testPro";
        proPlannerPlus.createProject(projectName);

    }
    @Given("missing info for the activity")
    public void missing_info_for_the_activity() {
        activityName = "";
        hoursPerWeek = 0;
        startDate = null;
        endDate = null;
        projectName = "";
        proPlannerPlus.createProject("testProject");
    }

    @Given("the end date is before the start date")
    public void the_end_date_is_before_the_start_date() {
        activityName = "testAct";
        hoursPerWeek = 10;
        startDate = LocalDate.of(2024, 05, 03);
        endDate = LocalDate.of(2023, 05, 03);
        projectName = "testPro";
        proPlannerPlus.createProject(projectName);

    }


    @When("the employee creates a activity")
    public void the_employee_creates_a_activity() throws OperationNotAllowedException {
        try {
            proPlannerPlus.createActivity(activityName, hoursPerWeek, startDate, endDate, projectName);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

    }

    @Then("an activity is created")
    public void an_activity_is_created() {
        assertTrue(proPlannerPlus.getActivities().stream().anyMatch(p -> p.getName().equals(activityName)));
    }





}
