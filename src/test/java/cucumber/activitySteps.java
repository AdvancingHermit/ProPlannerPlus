package cucumber;

import controllers.ProPlannerPlus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class activitySteps {

    ProPlannerPlus proPlannerPlus;
    String activityName;
    int hoursPerWeek;
    LocalDate startDate;
    LocalDate endDate;

    public activitySteps(){ proPlannerPlus = new ProPlannerPlus(); }



    @Given("the info of an activity")
    public void the_info_of_an_activity() {
        activityName = "testAct";
        hoursPerWeek = 10;
        startDate = LocalDate.of(2024, 05, 03);
        endDate = LocalDate.of(2024, 06, 03);
    }

    @When("the employee creates a activity")
    public void the_employee_creates_a_activity() {
        proPlannerPlus.createActivity(activityName, hoursPerWeek, startDate, endDate);
    }

    @Then("an activity is created")
    public void an_activity_is_created() {
        assertTrue(proPlannerPlus.getActivities().stream().anyMatch(p -> p.getName().equals(activityName)));
    }




}
