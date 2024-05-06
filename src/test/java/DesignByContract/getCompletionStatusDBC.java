package DesignByContract;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import model.Activity;
import model.Employee;
import model.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class getCompletionStatusDBC {
    private ProPlannerPlus proPlannerPlus;
    private Employee employee;
    private Project emptyProject;
    private Project project;
    private Activity activity;

    private LocalDate startDate;
    private LocalDate endDate;
    @BeforeEach
    void setUp() throws OperationNotAllowedException {
        proPlannerPlus = new ProPlannerPlus();
        project = new Project("project",1);
        emptyProject= new Project("empty",1);
        employee = new Employee("test");
        proPlannerPlus.createTestProject(project);
        proPlannerPlus.clearEmployees();
        proPlannerPlus.addEmployee(employee);

        proPlannerPlus.createActivity("testAct", 10, LocalDate.of(2024,05,03),
                LocalDate.of(2024,05,04), project.getId(), 10);
        proPlannerPlus.addActivityToProject(project,proPlannerPlus.getActivities().get(0).getActivityID());

        proPlannerPlus.addEmployeeDirectlyToActivity(proPlannerPlus.getActivities().get(0).getActivityID(), employee);
        employee.registerTime(10, 1);

    }

    @Test
    void testProjectNull() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> proPlannerPlus.getCompletionStatus(null));
        Assertions.assertEquals("Project cannot be null", assertionError.getMessage());
    }
    @Test
    void testActivityEmpty() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> proPlannerPlus.getCompletionStatus(emptyProject));
        Assertions.assertEquals("No activities exists in project", assertionError.getMessage());
    }

    @Test
    void checkCompletionStatusPostConditions() throws OperationNotAllowedException {
        proPlannerPlus.getCompletionStatus(project);
    }

}
