package junit;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import model.Activity;
import model.Employee;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import static org.junit.Assert.assertTrue;


/**
 * A test class to check which tests are run with which framework.
 * If run as a JUnit 5 (Jupiter) test, then both tests are run.
 * If run as a JUnit 4 test, then only the JUnit 4 tests are run.
 *
 * Remove the tests in your own projects.
 */
public class addEmployeeToActivityTest {

	ProPlannerPlus proPlannerPlus;
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@org.junit.Before // Junit 4
	public void setUp() {
		proPlannerPlus = new ProPlannerPlus();
	}

	@org.junit.Test // JUnit 4
	public void FreeEmployee() throws OperationNotAllowedException {
		expectedEx.expectMessage("Employee not available");
		Employee employee = new Employee("test");
		employee.addPersonalActivity(LocalDate.of(2024, 5, 5), LocalDate.of(2024, 7, 5), "Sick");
		proPlannerPlus.addEmployee(employee);
		Activity activity = new Activity("TestAc", 10, LocalDate.of(2024, 4,5),
				LocalDate.of(2025, 4, 5), 3);
		proPlannerPlus.createProject("TestPro");
		proPlannerPlus.createActivity(activity, "TestPro");
		proPlannerPlus.addEmployeeToActivity(3, employee);
	}
	@org.junit.Test // JUnit 4
	public void addsEmployee() throws OperationNotAllowedException {
		Employee employee = new Employee("test");
		proPlannerPlus.addEmployee(employee);
		proPlannerPlus.createProject("TestPro");
		Activity activity = new Activity("TestAc", 10, LocalDate.of(2024, 4,5),
				LocalDate.of(2025, 4, 5), 3);
		proPlannerPlus.createActivity(activity, "TestPro");
		proPlannerPlus.addEmployeeToActivity(3, employee);
		assertTrue(proPlannerPlus.getActivity(activity.getActivityID()).getEmployees().contains(employee));
	}
	@org.junit.Test // JUnit 4
	public void alreadyPartOfActivity() throws OperationNotAllowedException {
		expectedEx.expectMessage("Employee already assigned");
		Employee employee = new Employee("test");
		proPlannerPlus.addEmployee(employee);
		proPlannerPlus.createProject("TestPro");
		Activity activity = new Activity("TestAc", 10, LocalDate.of(2024, 4,5),
				LocalDate.of(2025, 4, 5), 3);
		proPlannerPlus.createActivity(activity, "TestPro");
		proPlannerPlus.addEmployeeDirectlyToActivity(3, employee);
		proPlannerPlus.addEmployeeToActivity(3, employee);
		proPlannerPlus.getActivity(activity.getActivityID()).getEmployees().contains(employee);
	}
}
