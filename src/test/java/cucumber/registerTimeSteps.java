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

public class registerTimeSteps {

    private ProPlannerPlus proPlannerPlus;

    private int activityID;

    private Employee employee;

    LocalDate start;
    LocalDate end;

    public registerTimeSteps() throws OperationNotAllowedException {
        proPlannerPlus = new ProPlannerPlus();
        employee = new Employee("yo");
        proPlannerPlus.addEmployee(employee);

    }


    @Given("an activity exists")
    public void an_activity_exists() throws OperationNotAllowedException {

        String activityName = "testAct";
        int hoursPerWeek = 10;
        LocalDate start = LocalDate.of(2024, 05, 03);
        LocalDate end = LocalDate.of(2024, 06, 03);
        String projectName = "testPro";
        proPlannerPlus.createProject(projectName);

        proPlannerPlus.createActivity(activityName, hoursPerWeek, start, end, projectName);

        activityID = proPlannerPlus.getProject(projectName).getActivityIDs().get(0);
    }

    @When("an employee registers a {double} hours for the activity")
    public void an_employee_registers_a_hours_for_the_activity(Double double1) {
        employee.registerTime(activityID, double1);
    }

    @Then("{double} hours of time has been registered for the activity for employee")
    public void hours_of_time_has_been_registered_for_the_activity_for_employee(Double double1) {
        assertTrue(employee.getTimeUsed(activityID) == double1);
    }

    @When("an employee registers time on the personal activity")
    public void an_employee_registers_time_on_the_personal_activity() throws OperationNotAllowedException {
        start = LocalDate.of(2024, 05, 03);
        end = LocalDate.of(2024, 06, 03);
        proPlannerPlus.addPersonalActivity(employee.getInitials(), start, end, "sick");
    }

    @Then("that amount of time has been registered for the personal activity for employee")
    public void that_amount_of_time_has_been_registered_for_the_personal_activity_for_employee() {
        LocalDate compStart = employee.getPersonalActivities().get(0).getStart();
        LocalDate compEnd = employee.getPersonalActivities().get(0).getEnd();

        assertEquals(start, compStart);
        assertEquals(end, compEnd);
    }

}
