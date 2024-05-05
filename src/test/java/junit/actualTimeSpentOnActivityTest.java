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
    private Project project;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @org.junit.Before // Junit 4
    public void setUp() {
        proPlannerPlus = new ProPlannerPlus();
    }

    @org.junit.Test // JUnit 4
    public void nonExistantID() throws OperationNotAllowedException {
        expectedEx.expectMessage("Activity doesn't exist");

        proPlannerPlus.actualTimeSpentOnActivity(0);
    }

    @org.junit.Test // JUnit 4
    public void employeesSizeZero() throws OperationNotAllowedException {

        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);

        Activity activity = new Activity("testAct", 10, LocalDate.of(2024, 05, 03),
                LocalDate.of(2024, 06, 03), 1);

        ProPlannerPlus.createActivity(activity, project.getId());

        assertEquals(0.0, proPlannerPlus.actualTimeSpentOnActivity(activity.getActivityID()), 0.0);
    }

    @org.junit.Test // JUnit 4
    public void negativeTimeUsed () throws OperationNotAllowedException {

        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);

        Activity activity = new Activity("testAct", 10, LocalDate.of(2024, 05, 03),
                LocalDate.of(2024, 06, 03), 1);


        ProPlannerPlus.createActivity(activity, project.getId());

        //expectedEx.expect(OperationNotAllowedException.class);
        expectedEx.expectMessage("Can't have negative time spent");

        proPlannerPlus.actualTimeSpentOnActivity(activity.getActivityID());

        proPlannerPlus.registerTime(activity.getActivityID(), "abe", -1);
    }

    @org.junit.Test // JUnit 4
    public void positiveTimeUsed () throws OperationNotAllowedException {

        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);
        Activity activity = new Activity("testAct", 10, LocalDate.of(2024, 05, 03),
                LocalDate.of(2024, 06, 03), 1);

        ProPlannerPlus.createActivity(activity, project.getId());

        proPlannerPlus.registerTime(activity.getActivityID(), "abe", 1);
        assertEquals(1.0, proPlannerPlus.actualTimeSpentOnActivity(activity.getActivityID()), 0.0);
    }


}
