package DesignByContract;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import model.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import model.Activity;
import model.Employee;
import model.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

//Made by Martin
public class addEmployeeToActivityDBC {
    private ProPlannerPlus proPlannerPlus;
    private Employee employee;

    private Project project;
    private Activity activity;

    @BeforeEach
    void setUp() throws OperationNotAllowedException {
        proPlannerPlus = new ProPlannerPlus();
        employee = new Employee("test");
        proPlannerPlus.addEmployee(employee);
        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);
        activity = new Activity("testAct", 10,LocalDate.of(2024,05,03),
                LocalDate.of(2024,05,04), 10);
        proPlannerPlus.createActivity( activity, project.getId());
        proPlannerPlus.addActivityToProject(project,activity.getActivityID());

    }
    @Test
    void employeeIsAdded() throws OperationNotAllowedException {
        proPlannerPlus.addEmployeeToActivity(activity.getActivityID(), employee);
        Assertions.assertTrue(proPlannerPlus.getActivity(activity.getActivityID()).getEmployees().contains(employee));
    }
    @Test
    void noMatchingActivitiesForID(){
        AssertionError assertionError = assertThrows(AssertionError.class, () -> proPlannerPlus.addEmployeeToActivity(0, employee));
        Assertions.assertEquals("No matching activity for the ID", assertionError.getMessage());
    }
}

