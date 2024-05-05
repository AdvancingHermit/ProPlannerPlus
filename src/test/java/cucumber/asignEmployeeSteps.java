package cucumber;

import controllers.ProPlannerPlus;
import controllers.OperationNotAllowedException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Activity;
import model.Employee;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class asignEmployeeSteps {

    private ErrorMessageHolder errorMessageHolder;

    ProPlannerPlus proPlannerPlus;
    Activity activity;
    public asignEmployeeSteps(ErrorMessageHolder errorMessageHolder) {
        proPlannerPlus = new ProPlannerPlus();
        this.errorMessageHolder = errorMessageHolder;
    }
    Employee employee;
    @Given("A free employee which is not assigned to the activity")
    public void an_free_employee_which_is_not_assigned_to_the_activity() throws OperationNotAllowedException {
        proPlannerPlus.createProject("TestProject");
        activity =  new Activity("TestActivity",
                4,
                LocalDate.of(2002, 3, 2),
                LocalDate.of(2003, 3, 2),
                42);
        proPlannerPlus.createActivity(activity, "TestProject");
        employee = new Employee("TestEmployee");
        proPlannerPlus.addEmployee(employee);
    }
    @Given("A free employee which is assigned to the activity")
    public void aFreeEmployeeWhichIsAssignedToTheActivity() throws OperationNotAllowedException {
        proPlannerPlus.createProject("TestProject");
        activity = new Activity("TestActivity",4,
                LocalDate.of(2002, 3, 2),
                LocalDate.of(2003, 3, 2),
                42);
        proPlannerPlus.createActivity(activity, "TestProject");
        employee = new Employee("TestEmployee");
        proPlannerPlus.addEmployee(employee);
        proPlannerPlus.getActivity(activity.getActivityID()).addEmployee(employee);
    }
    @When("The employee is assigned to the activity")
    public void The_employee_is_assigned_to_the_activity() throws OperationNotAllowedException{
        try {
            proPlannerPlus.addEmployeeToActivity(activity.getActivityID(), employee);
        }
        catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the error message {string} is given")
    public void then_an_error_message_is_given(String errorMessage) {
        assertEquals(errorMessage, this.errorMessageHolder.getErrorMessage());

    }

    @Then("The employee is now part of the activity")
    public void theEmployeeIsNowPartOfTheActivity() {
        assertTrue(proPlannerPlus.getActivity(activity.getActivityID()).getEmployees().contains(employee));
    }

    @Given("An non-free employee which is not assigned to the activity")
    public void anOccupiedEmployeeWhichIsNotAssignedToTheActivity() throws OperationNotAllowedException {
        proPlannerPlus.createProject("TestProject");
        activity = new Activity("TestActivity",4,
                LocalDate.of(2002, 3, 2),
                LocalDate.of(2003, 3, 2),
                42);
        proPlannerPlus.createActivity(activity,"TestProject");
        employee = new Employee("TestEmployee");
        proPlannerPlus.addEmployee(employee);
        employee.addPersonalActivity(LocalDate.of(2002, 3, 2),
                LocalDate.of(2003, 3, 3), "sick");
    }


}
