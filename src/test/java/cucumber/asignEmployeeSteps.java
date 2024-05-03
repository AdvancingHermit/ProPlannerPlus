package cucumber;

import controllers.ProPlannerPlus;
import controllers.OperationNotAllowedException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Employee;
import java.time.LocalDate;


import static org.junit.Assert.assertTrue;

public class asignEmployeeSteps {

    private ErrorMessageHolder errorMessageHolder;

    ProPlannerPlus proPlannerPlus;
    public asignEmployeeSteps(ErrorMessageHolder errorMessageHolder) {
        proPlannerPlus = new ProPlannerPlus();
        this.errorMessageHolder = errorMessageHolder;
    }
    Employee employee;
    @Given("A free employee which is not assigned to the activity")
    public void an_free_employee_which_is_not_assigned_to_the_activity() {
        // Write code here that turns the phrase above into concrete actions
        proPlannerPlus.createProject("TestProjekt");
        proPlannerPlus.createActivity("TestActivity",
                4,
                LocalDate.of(2002, 3, 2),
                LocalDate.of(2003, 3, 2),
                "TestProjekt");
        employee = new Employee("TestEmployee");
    }
    @Given("A free employee which is assigned to the activity")
    public void aFreeEmployeeWhichIsAssignedToTheActivity() {
        proPlannerPlus.createProject("TestProjekt");
        proPlannerPlus.createActivity("TestActivity",
                4,
                LocalDate.of(2002, 3, 2),
                LocalDate.of(2003, 3, 2),
                "TestProjekt");
        employee = new Employee("TestEmployee");
        proPlannerPlus.getActivity("TestActivity").addEmployee(employee);
    }
    @When("The employee is assigned to the activity")
    public void The_employee_is_assigned_to_the_activity(){

        try {
            proPlannerPlus.getActivity("TestActivity").addEmployee(employee);
        }
        catch (Exception e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

    }
    @Then("The employee is now part of the activity")
    public void theEmployeeIsNowPartOfTheActivity() {
        assertTrue(proPlannerPlus.getActivity("TestActivity").getEmployees().contains(employee));
    }

    @Given("An non-free employee which is not assigned to the activity")
    public void anOccupiedEmployeeWhichIsNotAssignedToTheActivity() {
    }


}
