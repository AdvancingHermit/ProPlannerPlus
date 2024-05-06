package DesignByContract;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import model.Activity;
import model.Employee;
import model.Project;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

//This test is mady by Oliver
public class getFreeEmployeesDBC {
    private ProPlannerPlus proPlannerPlus;
    private Employee employee;

    private Project project;

    private LocalDate startDate;

    private LocalDate endDate;

    @BeforeEach
    void setUp() throws OperationNotAllowedException {
        proPlannerPlus = new ProPlannerPlus();
        project = new Project("testProj",1);
        employee = new Employee("test");
        proPlannerPlus.createTestProject(project);
        proPlannerPlus.clearEmployees();
        proPlannerPlus.addEmployee(employee);
        proPlannerPlus.createActivity("testAct", 10,LocalDate.of(2024,05,03),
                LocalDate.of(2024,05,04), project.getId());
        proPlannerPlus.addActivityToProject(project,proPlannerPlus.getActivities().get(0).getActivityID());
        proPlannerPlus.addEmployeeDirectlyToActivity(proPlannerPlus.getActivities().get(0).getActivityID(), employee);

        startDate =  LocalDate.of(2024,05,03);
        endDate =  LocalDate.of(2024,05,04);

    }

    @Test
    void testValidGetFreeEmployees() throws OperationNotAllowedException {
        Map<Employee, Integer> expectedFreeEmployees = new LinkedHashMap<>();
        expectedFreeEmployees.put(employee, 1);
        Map<Employee, Integer> freeEmployees = proPlannerPlus.getFreeEmployees(startDate, endDate);

        Assertions.assertEquals(expectedFreeEmployees, freeEmployees);
    }

    @Test
    void testStartDateAfterEndDate() {
        startDate = endDate.plusDays(1);
        AssertionError assertionError = assertThrows(AssertionError.class, () -> proPlannerPlus.
                getFreeEmployees(startDate,endDate));
        Assertions.assertEquals("Start date can not be after end date", assertionError.getMessage());
    }

    @Test
    void testNullActivities() {
        proPlannerPlus.setActivities(null);
        AssertionError assertionError = assertThrows(AssertionError.class, () -> proPlannerPlus.
                getFreeEmployees(startDate,endDate));
        Assertions.assertEquals("Activities list cannot be null", assertionError.getMessage());
    }

    @Test
    void testNullEmployees() {
        proPlannerPlus.setEmployees(null);
        AssertionError assertionError = assertThrows(AssertionError.class, () -> proPlannerPlus.
                getFreeEmployees(startDate,endDate));
        Assertions.assertEquals("Employees list cannot be null", assertionError.getMessage());
    }






}
