package junit;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import model.Activity;
import model.Employee;
import model.Project;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class actualTimeSpentOnActivityTest {

    ProPlannerPlus proPlannerPlus;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @org.junit.Before // Junit 4
    public void setUp() {
        proPlannerPlus = new ProPlannerPlus();
    }

    @org.junit.Test // JUnit 4
    public void nonExistantID() throws OperationNotAllowedException {
        expectedEx.expect(OperationNotAllowedException.class);
        expectedEx.expectMessage("Activity doesn't exist");

        proPlannerPlus.actualTimeSpentOnActivity(0);
    }

    @org.junit.Test // JUnit 4
    public void employeesSizeZero() throws OperationNotAllowedException {

        String project = "test";

        Activity activity = new Activity("testAct", 10, LocalDate.of(2024, 05, 03),
                LocalDate.of(2024, 06, 03), 1);

        proPlannerPlus.createProject(project);
        ProPlannerPlus.createActivity(activity, project);

        assertEquals(0.0, proPlannerPlus.actualTimeSpentOnActivity(activity.getActivityID()), 0.0);
    }

    @org.junit.Test // JUnit 4
    public void negativeTimeUsed () throws OperationNotAllowedException {

        String project = "test";

        Activity activity = new Activity("testAct", 10, LocalDate.of(2024, 05, 03),
                LocalDate.of(2024, 06, 03), 1);

        proPlannerPlus.createProject(project);
        ProPlannerPlus.createActivity(activity, project);

        expectedEx.expect(OperationNotAllowedException.class);
        expectedEx.expectMessage("Can't have negative time spent");

        proPlannerPlus.registerTime(activity.getActivityID(), "abe", -1);
    }

    @org.junit.Test // JUnit 4
    public void positiveTimeUsed () throws OperationNotAllowedException {

        String project = "test";

        Activity activity = new Activity("testAct", 10, LocalDate.of(2024, 05, 03),
                LocalDate.of(2024, 06, 03), 1);

        proPlannerPlus.createProject(project);
        ProPlannerPlus.createActivity(activity, project);

        proPlannerPlus.registerTime(activity.getActivityID(), "abe", 1);
        assertEquals(1.0, proPlannerPlus.actualTimeSpentOnActivity(activity.getActivityID()), 0.0);
    }


}
