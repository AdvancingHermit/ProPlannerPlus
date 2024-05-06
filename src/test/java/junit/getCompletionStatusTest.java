package junit;

import model.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import model.Activity;
import model.Employee;
import model.Project;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.*;


//Made by Oscar
public class getCompletionStatusTest {

    private Project project;
    ProPlannerPlus proPlannerPlus;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @org.junit.Before // Junit 4
    public void setUp() {
        proPlannerPlus = new ProPlannerPlus();
    }

    @org.junit.Test //
    public void allActivitiesFinishedWithTimeSpent() throws OperationNotAllowedException {
        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);
        Activity activity = new Activity("testActivity", 1, null, null, 0);
        activity.setCompletion(true);
        Employee employee = new Employee("test");
        activity.addEmployee(employee);
        employee.registerTime(0, 1);
        proPlannerPlus.createActivity(activity, project.getId());
        assertEquals(proPlannerPlus.getCompletionStatus(proPlannerPlus.getProject(project.getId())), 1.0, 0.0);
    }
    @org.junit.Test //
    public void allActivitiesFinishedNoTimeSpent() throws OperationNotAllowedException {
        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);
        Activity activity = new Activity("testActivity", 1, null, null, 0);
        activity.setCompletion(true);
        Employee employee = new Employee("test");
        activity.addEmployee(employee);
        employee.registerTime(0, 0);
        proPlannerPlus.createActivity(activity, project.getId());
        assertEquals(proPlannerPlus.getCompletionStatus(proPlannerPlus.getProject(project.getId())), 1.0, 0.0);
    }
    @org.junit.Test //
    public void activitiesNotFinishedTotalTime0() throws OperationNotAllowedException {
        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);
        Activity activity = new Activity("testActivity", 1, null, null, 0);
        activity.setCompletion(false);
        proPlannerPlus.createActivity(activity, project.getId());
        assertEquals(proPlannerPlus.getCompletionStatus(proPlannerPlus.getProject(project.getId())), 0.0, 0.0);
    }
    @org.junit.Test //
    public void activitiesNotFinishedTotalTime1() throws OperationNotAllowedException {
        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);
        Activity activity = new Activity("testActivity", 0, null, null, 0);
        activity.setCompletion(false);
        proPlannerPlus.createActivity(activity, project.getId());
        assertEquals(proPlannerPlus.getCompletionStatus(proPlannerPlus.getProject(project.getId())), 1.0, 0.0);
    }
    @org.junit.Test
    public void noActivitiesInProject() throws OperationNotAllowedException {
        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);
        expectedEx.expectMessage("No activities exists in project");
        proPlannerPlus.getCompletionStatus(proPlannerPlus.getProject(project.getId()));
    }
}
