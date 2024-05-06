package junit;

import model.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import model.Employee;
import model.Project;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
//This test is made by Oliver
public class getFreeEmployeeTest {

    ProPlannerPlus proPlannerPlus;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @org.junit.Before // Junit 4
    public void setUp() {
        proPlannerPlus = new ProPlannerPlus();
    }

    @org.junit.Test // JUnit 4
    public void noActivities() throws OperationNotAllowedException{
        expectedEx.expectMessage("No activities exist");
        proPlannerPlus.getFreeEmployees(
                LocalDate.of(2024,05,03),
                LocalDate.of(2024,05,03));
    }
    @org.junit.Test // JUnit 4
    public void personalActivitiesWithNoOverlap() throws OperationNotAllowedException {
        Project project = new Project("testProj",1);
        Employee employee = new Employee("test");
        proPlannerPlus.createTestProject(project);
        proPlannerPlus.clearEmployees();
        proPlannerPlus.addEmployee(employee);
        proPlannerPlus.getEmployees().get(0).addPersonalActivity(LocalDate.of(2024,05,02),
                LocalDate.of(2024,05,10), "sick");
        proPlannerPlus.createActivity("testAct", 10,LocalDate.of(2024,05,03),
                LocalDate.of(2024,05,04), project.getId());
        proPlannerPlus.addActivityToProject(project,proPlannerPlus.getActivities().get(0).getActivityID());
        proPlannerPlus.addEmployeeDirectlyToActivity(proPlannerPlus.getActivities().get(0).getActivityID(), employee);


        LocalDate startDate =  LocalDate.of(2024,05,05);
        LocalDate endDate =  LocalDate.of(2024,05,06);

        Map<Employee, Integer> freeEmployees = new LinkedHashMap<>();

        assertEquals(freeEmployees, proPlannerPlus.getFreeEmployees(startDate,endDate));

    }

    @org.junit.Test // JUnit 4
    public void personalActivitiesWithOverlap() throws OperationNotAllowedException {
        Project project = new Project("testProj",1);
        Employee employee = new Employee("test");
        proPlannerPlus.createTestProject(project);
        proPlannerPlus.clearEmployees();
        proPlannerPlus.addEmployee(employee);
        proPlannerPlus.getEmployees().get(0).addPersonalActivity(LocalDate.of(2024,05,02),
                LocalDate.of(2024,05,10), "sick");
        proPlannerPlus.createActivity("testAct", 10,LocalDate.of(2024,05,03),
                LocalDate.of(2024,05,04), project.getId());
        proPlannerPlus.addActivityToProject(project,proPlannerPlus.getActivities().get(0).getActivityID());
        proPlannerPlus.addEmployeeDirectlyToActivity(proPlannerPlus.getActivities().get(0).getActivityID(), employee);


        LocalDate startDate =  LocalDate.of(2024,05,03);
        LocalDate endDate =  LocalDate.of(2024,05,04);

        Map<Employee, Integer> freeEmployees = new LinkedHashMap<>();

        assertEquals(freeEmployees, proPlannerPlus.getFreeEmployees(startDate,endDate));

    }

    @org.junit.Test // JUnit 4
    public void noPersonalActivitiesWithNoOverlap() throws OperationNotAllowedException {
        Project project = new Project("testProj",1);
        Employee employee = new Employee("test");
        proPlannerPlus.createTestProject(project);
        proPlannerPlus.clearEmployees();
        proPlannerPlus.addEmployee(employee);
        proPlannerPlus.createActivity("testAct", 10,LocalDate.of(2024,05,03),
                LocalDate.of(2024,05,04), project.getId());
        proPlannerPlus.addActivityToProject(project,proPlannerPlus.getActivities().get(0).getActivityID());
        proPlannerPlus.addEmployeeDirectlyToActivity(proPlannerPlus.getActivities().get(0).getActivityID(), employee);


        LocalDate startDate =  LocalDate.of(2024,05,05);
        LocalDate endDate =  LocalDate.of(2024,05,06);

        Map<Employee, Integer> freeEmployees = new LinkedHashMap<>();
        freeEmployees.put(employee,0);


        assertEquals(freeEmployees, proPlannerPlus.getFreeEmployees(startDate,endDate));

    }
    @org.junit.Test // JUnit 4
    public void noPersonalActivitiesWithOverlap() throws OperationNotAllowedException {
        Project project = new Project("testProj",1);
        Employee employee = new Employee("test");
        proPlannerPlus.createTestProject(project);
        proPlannerPlus.clearEmployees();
        proPlannerPlus.addEmployee(employee);
        proPlannerPlus.createActivity("testAct", 10,LocalDate.of(2024,05,03),
                LocalDate.of(2024,05,04), project.getId());
        proPlannerPlus.addActivityToProject(project,proPlannerPlus.getActivities().get(0).getActivityID());
        proPlannerPlus.addEmployeeDirectlyToActivity(proPlannerPlus.getActivities().get(0).getActivityID(), employee);


        LocalDate startDate =  LocalDate.of(2024,05,03);
        LocalDate endDate =  LocalDate.of(2024,05,04);

        Map<Employee, Integer> freeEmployees = new LinkedHashMap<>();
        freeEmployees.put(employee,1);

        assertEquals(freeEmployees, proPlannerPlus.getFreeEmployees(startDate,endDate));

    }



}
