package cucumber;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import model.Employee;

public class addRemoveEmployeeSteps {
    private ErrorMessageHolder errorMessageHolder;

    ProPlannerPlus proPlannerPlus;
    public addRemoveEmployeeSteps(ErrorMessageHolder errorMessageHolder) {
        proPlannerPlus = new ProPlannerPlus();
        this.errorMessageHolder = errorMessageHolder;
    }
    Employee employee;
    @Given("an employee wants to add a new employee")
    public void an_employee_wants_to_add_a_new_employee() {
        employee = new Employee("test");
    }
    @When("the employee adds the new employee")
    public void the_employee_adds_the_new_employee() throws OperationNotAllowedException {
        proPlannerPlus.addEmployee(employee);
    }
    @Then("the new employee is added to employees.")
    public void the_new_employee_is_added_to_employees() {
        assertTrue(proPlannerPlus.getEmployees().contains(employee));
    }


    @Given("an employee who adds an existing employee")
    public void an_employee_who_adds_an_existing_employee() throws OperationNotAllowedException {
        employee = new Employee("test");
        proPlannerPlus.addEmployee(employee);
    }
    @When("the employee adds the employee")
    public void the_employee_adds_the_employee() throws OperationNotAllowedException {
        try {
            proPlannerPlus.addEmployee(employee);
        } catch (OperationNotAllowedException e){
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

    }
    @Then("Then An error message {string} is given")
    public void then_an_error_message_is_given(String errorMessage) {
        assertEquals(errorMessage, this.errorMessageHolder.getErrorMessage());

    }

    @Given("an employee wants to remove an employee")
    public void an_employee_wants_to_remove_an_employee() throws OperationNotAllowedException {
        employee = new Employee("test");
        proPlannerPlus.addEmployee(employee);


    }
    @When("the employee wants to remove the employee")
    public void the_employee_wants_to_remove_the_employee() throws OperationNotAllowedException {
        proPlannerPlus.removeEmployee(employee.getInitials());
    }
    @Then("the employee is removed from employees.")
    public void the_employee_is_removed_from_employees() {
        assertFalse(proPlannerPlus.getEmployees().contains(employee));
    }


    @Given("an employee who tries to remove a non existing employee")
    public void an_employee_who_tries_to_remove_a_non_existing_employee() {
        employee = new Employee("test");
    }
    @When("the employee tries to remove the employee")
    public void the_employee_tries_to_remove_the_employee() {
        try {
            proPlannerPlus.removeEmployee(employee.getInitials());
        } catch (OperationNotAllowedException e){
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
    @Then("An error message {string} is given")
    public void an_error_message_is_given(String errorMessage) {
        assertEquals(errorMessage, this.errorMessageHolder.getErrorMessage());

    }

}
