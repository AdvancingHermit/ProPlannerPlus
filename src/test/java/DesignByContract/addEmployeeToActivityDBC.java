package DesignByContract;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import model.Employee;
import model.Project;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;


public class addEmployeeToActivityDBC {
    private ProPlannerPlus proPlannerPlus;
    private Employee employee;

    private Project project;

    private LocalDate startDate;

    private LocalDate endDate;


    @BeforeEach
    void setUp() throws OperationNotAllowedException {
        proPlannerPlus = new ProPlannerPlus();
        employee = new Employee("test");
        project = new Project("test",1);
        proPlannerPlus.createTestProject(project);
        proPlannerPlus.clearEmployees();
        proPlannerPlus.addEmployee(employee);
        proPlannerPlus.createActivity("testAct", 10,LocalDate.of(2024,05,03),
                LocalDate.of(2024,05,04), project.getId());
        proPlannerPlus.addActivityToProject(project,proPlannerPlus.getActivities().get(0).getActivityID());
        proPlannerPlus.addEmployeeDirectlyToActivity(proPlannerPlus.getActivities().get(0).getActivityID(), employee);
    }
    @Test
    void employeeIsAdded() {

    }
    @Test
    void noMatchingActivitiesForID(){

    }
}

