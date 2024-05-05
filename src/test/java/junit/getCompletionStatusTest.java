package junit;

import controllers.ProPlannerPlus;
import model.Activity;
import model.Employee;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.*;

public class getCompletionStatusTest {


    ProPlannerPlus proPlannerPlus;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @org.junit.Before // Junit 4
    public void setUp() {
        proPlannerPlus = new ProPlannerPlus();
    }

    @org.junit.Test //
    public void allActivitiesFinishedWithTimeSpent() {
        proPlannerPlus.createProject("test");
        Activity activity = new Activity("testActivity", 1, null, null, 0);
        activity.setCompletion(true);
        Employee employee = new Employee("test");
        activity.addEmployee(employee);
        employee.registerTime(0, 1);
        proPlannerPlus.createActivity(activity, "test");
        assertEquals(proPlannerPlus.getCompletionStatus(proPlannerPlus.getProject("test")), 1.0, 0.0);
    }
    @org.junit.Test //
    public void allActivitiesFinishedNoTimeSpent() {
        proPlannerPlus.createProject("test");
        Activity activity = new Activity("testActivity", 1, null, null, 0);
        activity.setCompletion(true);
        Employee employee = new Employee("test");
        activity.addEmployee(employee);
        employee.registerTime(0, 0);
        proPlannerPlus.createActivity(activity, "test");
        assertEquals(proPlannerPlus.getCompletionStatus(proPlannerPlus.getProject("test")), 1.0, 0.0);
    }
    @org.junit.Test //
    public void activitiesNotFinishedTotalTime0() {
        proPlannerPlus.createProject("test");
        Activity activity = new Activity("testActivity", 1, null, null, 0);
        activity.setCompletion(false);
        proPlannerPlus.createActivity(activity, "test");
        assertEquals(proPlannerPlus.getCompletionStatus(proPlannerPlus.getProject("test")), 0.0, 0.0);
    }
    @org.junit.Test //
    public void activitiesNotFinishedTotalTime1() {
        proPlannerPlus.createProject("test");
        Activity activity = new Activity("testActivity", 0, null, null, 0);
        activity.setCompletion(false);
        proPlannerPlus.createActivity(activity, "test");
        assertEquals(proPlannerPlus.getCompletionStatus(proPlannerPlus.getProject("test")), 1.0, 0.0);
    }
    @org.junit.Test
    public void noActivitiesInProject() {
        proPlannerPlus.createProject("test");
        assertThrows(NullPointerException.class, ()-> proPlannerPlus.getCompletionStatus(proPlannerPlus.getProject("test")));
    }
}
