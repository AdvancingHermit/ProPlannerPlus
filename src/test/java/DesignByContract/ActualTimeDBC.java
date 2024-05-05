package DesignByContract;

import static org.junit.jupiter.api.Assertions.*;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import model.Activity;
import model.Employee;
import model.Project;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;

class ActualTimeDBC {

    private ProPlannerPlus proPlannerPlus;
    @Rule
    public ExpectedException expectedEx;


    @BeforeEach
    void setUp() {
        proPlannerPlus = new ProPlannerPlus();
        expectedEx = ExpectedException.none();
    }

    @Test
    void testNullActivities() {
        proPlannerPlus.setActivities(null);
        AssertionError assertionError = assertThrows(AssertionError.class, () ->  proPlannerPlus.actualTimeSpentOnActivity(0));
        Assertions.assertEquals("Activities list cannot be null", assertionError.getMessage());
    }

    @Test
    void testNullEmployees() {
        proPlannerPlus.setEmployees(null);
        AssertionError assertionError = assertThrows(AssertionError.class, () -> proPlannerPlus.actualTimeSpentOnActivity(0));
        Assertions.assertEquals("Employees list cannot be null", assertionError.getMessage());
    }
    @Test
    void negativeTimeSpent() throws OperationNotAllowedException {
        Project project = new Project("pro", 0);
        proPlannerPlus.createTestProject(project);

        Activity activity = new Activity("testAct", 10, LocalDate.of(2024, 05, 03),
                LocalDate.of(2024, 06, 03), 0);
        ProPlannerPlus.createActivity(activity, project.getId());
        activity.setCompletion(true);

        Employee employee1 = new Employee("test");
        proPlannerPlus.addEmployee(employee1);
        activity.addEmployee(employee1);

        employee1.registerTime(0, 0);
        employee1.setTimeUsed(0, -1);

        Throwable exception = assertThrows( Throwable.class,  () -> proPlannerPlus.actualTimeSpentOnActivity(0) );
        assertTrue(exception instanceof OperationNotAllowedException || exception instanceof AssertionError);
        assertEquals("Can't have negative time spent", exception.getMessage());

    }
}
