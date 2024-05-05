package cucumber;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Activity;
import model.Project;
import org.junit.Assert;


import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class activitySteps {

    ProPlannerPlus proPlannerPlus;
    String activityName;
    int hoursPerWeek;
    LocalDate startDate;
    LocalDate endDate;
    String projectName;
    Activity activity;
    Activity activity2;

    private Project project;

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
        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);

    }
    @Given("missing info for the activity")
    public void missing_info_for_the_activity() {
        activityName = "";
        hoursPerWeek = 0;
        startDate = null;
        endDate = null;
        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);
    }

    @Given("the end date is before the start date")
    public void the_end_date_is_before_the_start_date() {
        activityName = "testAct";
        hoursPerWeek = 10;
        startDate = LocalDate.of(2024, 05, 03);
        endDate = LocalDate.of(2023, 05, 03);
        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);

    }


    @When("the employee creates a activity")
    public void the_employee_creates_a_activity() throws OperationNotAllowedException {
        try {
            proPlannerPlus.createActivity(activityName, hoursPerWeek, startDate, endDate, project.getId());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

    }

    @Then("an activity is created")
    public void an_activity_is_created() {
        assertTrue(proPlannerPlus.getActivities().stream().anyMatch(p -> p.getName().equals(activityName)));
    }

    @When("the employee modifies the activity")
    public void theEmployeeModifiesTheActivity() throws OperationNotAllowedException {
        activity2 = new Activity("testAct2", 20,LocalDate.of(2025, 05, 03),
                LocalDate.of(2025, 06, 03), 1);

        proPlannerPlus.modifyActivity(activity.getActivityID(), activity2.getName(), activity2.getTotalTime(),
                activity2.getStartDate(), activity2.getEndDate(), project.getId(), activity2.getComplete());

    }

    @Then("the activity is now modified")
    public void theActivityIsNowModified() {
        Activity finalActivity = proPlannerPlus.getActivity(activity.getActivityID());
        assertEquals(activity2.getActivityID(), finalActivity.getActivityID());
        assertEquals(activity2.getStartDate(), finalActivity.getStartDate());
        assertEquals(activity2.getEndDate(), finalActivity.getEndDate());
        assertEquals(activity2.getTotalTime(), finalActivity.getTotalTime(), 0.0);
        assertEquals(activity2.getComplete(), finalActivity.getComplete());

    }
    @Given("an activity in the project")
    public void anActivityInTheProject() throws OperationNotAllowedException {
        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);
        activity = new Activity("testAct1", 10,LocalDate.of(2024, 05, 03),
                LocalDate.of(2024, 06, 03), 1);
        proPlannerPlus.createActivity(activity.getName(), activity.getTotalTime(), activity.getStartDate(), activity.getEndDate(), project.getId(), 1);
    }





}
