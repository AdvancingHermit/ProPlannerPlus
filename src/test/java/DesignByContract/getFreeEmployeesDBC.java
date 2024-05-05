package DesignByContract;
import static org.junit.Assert.assertEquals;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import model.Activity;
import model.Employee;
import model.Project;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class getFreeEmployeesDBC {
    private ProPlannerPlus proPlannerPlus;
    private Employee employee;

    private Activity activity;

    private Project project;

    @BeforeEach
    void setUp() throws OperationNotAllowedException {
        project = new Project("testProj",1);
        employee = new Employee("test");
        proPlannerPlus.createProject(project.getName());
        proPlannerPlus.clearEmployees();
        proPlannerPlus.addEmployee(employee);
        proPlannerPlus.getEmployees().get(0).addPersonalActivity(LocalDate.of(2024,05,02),
                LocalDate.of(2024,05,10), "sick");
        proPlannerPlus.createActivity("testAct", 10,LocalDate.of(2024,05,03),
                LocalDate.of(2024,05,04), "testProj");
        proPlannerPlus.addActivityToProject(project,proPlannerPlus.getActivities().get(0).getActivityID());
        proPlannerPlus.addEmployeeDirectlyToActivity(proPlannerPlus.getActivities().get(0).getActivityID(), employee);


        LocalDate startDate =  LocalDate.of(2024,05,03);
        LocalDate endDate =  LocalDate.of(2024,05,04);

        Map<Employee, Integer> freeEmployees = new LinkedHashMap<>();

        assertEquals(freeEmployees, proPlannerPlus.getFreeEmployees(startDate,endDate));
    }
}
