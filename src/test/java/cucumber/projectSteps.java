package cucumber;

import controllers.ProPlannerPlus;
import model.Employee;
import model.Project;
import io.cucumber.java.an.E;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class projectSteps {
    ProPlannerPlus proPlannerPlus;
    public projectSteps() {
        proPlannerPlus = new ProPlannerPlus();
    }

    String projectName = "";
    Project project;
    Employee employee;

    @Given("The name of the project")
    public void the_name_of_the_project() {
        projectName = "test";
    }
    @When("the employee creates a project")
    public void the_employee_creates_a_project() {
        proPlannerPlus.createProject(projectName);
    }
    @Then("a project is created")
    public void a_project_is_created() {
        assertTrue(proPlannerPlus.getProjects().stream().anyMatch(p -> p.getName().equals(projectName)));

    }

    @Given("a project exists")
    public void a_project_exists() {
        proPlannerPlus.createProject("test");
    }
    @Given("an employee exists")
    public void an_employee_exists() {
        employee = new Employee("test");
        proPlannerPlus.addEmployees(employee);
    }
    @When("employee is assigned as project leader")
    public void employee_is_assigned_as_project_leader() {
        project = proPlannerPlus.getProjects().stream().filter(p -> "test".equals(p.getName())).
                                                        findFirst().orElse(null) ;
        proPlannerPlus.assignProjectLeader(project, employee);
    }
    @Then("employee is the project leader")
    public void employee_is_the_project_leader() {
        assertTrue(project.getProjectLeader() == employee);

    }
}
