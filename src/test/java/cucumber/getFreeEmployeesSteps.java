package cucumber;

import model.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Activity;
import model.Employee;
import model.Project;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.Assert.assertTrue;

//This test is made by Oliver
public class getFreeEmployeesSteps {

    ProPlannerPlus proPlannerPlus;

    Project project;

    Employee employee;


    Activity activity;

    Map<Employee, Integer> freeEmployees;

    public getFreeEmployeesSteps(){ proPlannerPlus = new ProPlannerPlus(); }
    @Given("at least {int} free employee exists")
    public void at_least_employee_exists(Integer int1) throws OperationNotAllowedException {
        employee = new Employee("test");
        project = new Project("Test", 1);
        activity = new Activity("testAct", 10, LocalDate.of(2024, 05, 03),
                LocalDate.of(2024, 06, 03), 1);
        proPlannerPlus.addEmployee(employee);
        proPlannerPlus.createTestProject(project);
        proPlannerPlus.createActivity(activity, project.getId());
    }
    @When("the employee requests a list of free employees.")
    public void the_employee_requests_a_list_of_free_employees() throws OperationNotAllowedException {
        freeEmployees = proPlannerPlus.getFreeEmployees(
                 activity.getStartDate()
                , activity.getEndDate());
    }
    @Then("a list of free employees are given.")
    public void a_list_of_free_employees_are_given() {
        assertTrue(freeEmployees.containsKey(employee));
    }
}
