package controllers;

import model.Employee;
import model.Project;

import java.time.Year;
import java.util.ArrayList;

public class ProPlannerPlus {
    static ArrayList<Employee> employees;
    static ArrayList<Project> projects;
    public static boolean loggedIn = false;

    public ProPlannerPlus() {
        employees = new ArrayList<Employee>();
        employees.add(new Employee("abe"));
        projects = new ArrayList<Project>();
    }

    public static ArrayList<Employee> getEmployees() {
        return employees;
    }

    public static void addEmployees(Employee employee) {
        employees.add(employee);
    }

    public static boolean login(String initials) {
        for (Employee employee : ProPlannerPlus.getEmployees()) {
            if (employee.getInitials().equals(initials)) {
                loggedIn = true;
                return true;
            }
        }
        return false;

    }

    public static void createProject(String name) {
        long count = projects.stream().filter(p -> p.getName().equals(name)).count();
        String serialNumber = String.format("%04d", count + 1);
        int projectID = Integer.parseInt((Year.now().getValue() - 2000) + serialNumber);
        projects.add(new Project(name, projectID));

    }


    public static ArrayList<Project> getProjects() {
        return projects;
    }

    public static void assignProjectLeader(Project project, Employee employee) {
        project.setProjectLeader(employee);
    }
}
