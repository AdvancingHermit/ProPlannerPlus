package cucumber;

import controllers.ProPlannerPlus;
import model.OperationNotAllowedException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Activity;
import model.Employee;
import model.Project;

import java.time.LocalDate;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//Made by Martin and Oliver
public class assignEmployeeSteps {

    private ErrorMessageHolder errorMessageHolder;

    private Project project;

    ProPlannerPlus proPlannerPlus;
    Activity activity;
    public assignEmployeeSteps(ErrorMessageHolder errorMessageHolder) {
        proPlannerPlus = new ProPlannerPlus();
        this.errorMessageHolder = errorMessageHolder;
    }
    Employee employee;
    @Given("A free employee which is not assigned to the activity")
    public void an_free_employee_which_is_not_assigned_to_the_activity() throws OperationNotAllowedException {
        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);
        activity =  new Activity("TestActivity",
                4,
                LocalDate.of(2002, 3, 2),
                LocalDate.of(2003, 3, 2),
                42);
        proPlannerPlus.createActivity(activity, project.getId());
        employee = new Employee("test");
        proPlannerPlus.addEmployee(employee);
    }
    @Given("A free employee which is assigned to the activity")
    public void aFreeEmployeeWhichIsAssignedToTheActivity() throws OperationNotAllowedException {
        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);
        activity = new Activity("TestActivity",4,
                LocalDate.of(2002, 3, 2),
                LocalDate.of(2003, 3, 2),
                42);
        proPlannerPlus.createActivity(activity, project.getId());
        employee = new Employee("test");
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
        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);
        activity = new Activity("TestActivity",4,
                LocalDate.of(2002, 3, 2),
                LocalDate.of(2003, 3, 2),
                42);
        proPlannerPlus.createActivity(activity,project.getId());
        employee = new Employee("test");
        proPlannerPlus.addEmployee(employee);
        employee.addPersonalActivity(LocalDate.of(2002, 3, 2),
                LocalDate.of(2003, 3, 3), "sick");
    }


}
