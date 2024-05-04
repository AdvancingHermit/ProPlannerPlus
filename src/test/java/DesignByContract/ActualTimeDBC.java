package DesignByContract;

import static org.junit.jupiter.api.Assertions.*;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import model.Activity;
import model.Employee;
import model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class ActualTimeDBC {

    private ProPlannerPlus planner;
    private Employee employee1;
    private Employee employee2;
    private Activity activity;

    @BeforeEach
    void setUp() throws OperationNotAllowedException {
        planner = new ProPlannerPlus();
        employee1 = new Employee("Abe");
        employee2 = new Employee("Yoe");

        String project = "hey";
        planner.createProject(project);

        activity = new Activity("Yo", 0, LocalDate.now(), LocalDate.now().plusDays(10), 1);

        planner.addEmployee(employee1);
        planner.addEmployee(employee2);

        planner.registerTime(activity.getActivityID(), employee1.getInitials(), 5.0);
        planner.registerTime(activity.getActivityID(), employee2.getInitials(), 3.5);

        ProPlannerPlus.createActivity(activity, project);
    }

    @Test
    void testValidActivityTimeCalculation() throws OperationNotAllowedException {
        double timeSpent = planner.actualTimeSpentOnActivity(1);
        assertEquals(8.5, timeSpent, "Total time spent should be correctly calculated");
    }

    @Test
    void testActivityIDNotFound() {
        Exception exception = assertThrows(OperationNotAllowedException.class, () -> planner.actualTimeSpentOnActivity(999));
        assertEquals("Activity doesn't exist", exception.getMessage());
    }

    @Test
    void testNegativeTimeRegistration() throws OperationNotAllowedException {
        Exception exception = assertThrows(OperationNotAllowedException.class, () -> planner.registerTime(activity.getActivityID(), employee1.getInitials(), -3.0));
        assertEquals("Can't have negative time spent", exception.getMessage());
    }
}
