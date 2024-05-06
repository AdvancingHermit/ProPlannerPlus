package cucumber;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import model.Employee;

//made by Oscar
public class loginSteps {
	ProPlannerPlus proPlannerPlus;
	public loginSteps() {
		proPlannerPlus = new ProPlannerPlus();
	}

	@Given("the employee is not logged in and the employee is registered with their initials")
	public void the_employee_is_not_logged_in_and_the_employee_is_registered_with_their_initials() throws OperationNotAllowedException {
		proPlannerPlus.addEmployee(new Employee("test"));
	}
	@When("an employee logs in using their initials")
	public void an_employee_logs_in_using_their_initials() {
		proPlannerPlus.login("test");
	}
	@Then("the employee successfully logs in")
	public void the_employee_successfully_logs_in() {
		assertTrue(proPlannerPlus.loggedIn);
	}

	@Given("the employee is not logged in")
	public void the_employee_is_not_logged_in() {
		proPlannerPlus.loggedIn = false;
	}
	@When("an employee tries to log in with initials not in the system")
	public void an_employee_tries_to_log_in_with_initials_not_in_the_system() {
		proPlannerPlus.login("-1");
	}
	@Then("the employee fails to logs in")
	public void the_employee_fails_to_logs_in() {
		assertFalse(proPlannerPlus.loggedIn);
	}


}
