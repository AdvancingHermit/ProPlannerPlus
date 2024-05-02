package controllers;

import model.Activity;
import model.Employee;
import model.Project;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;

public class ProPlannerPlus {
    static ArrayList<Employee> employees;
    static ArrayList<Project> projects;
    static ArrayList<Activity> activities;
    public static boolean loggedIn = false;


    public ProPlannerPlus() {
        employees = new ArrayList<Employee>();
        employees.add(new Employee("abe"));
        projects = new ArrayList<Project>();
        activities = new ArrayList<Activity>();
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

    public static void createActivity(String activityName, int hoursPerWeek, Date startDate, Date endDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String start = sdf.format(startDate);
        String end = sdf.format(endDate);
        String baseId = activityName + hoursPerWeek + start + end;
        int activityID = Math.abs(baseId.hashCode());
        activities.add(new Activity(activityName, hoursPerWeek, startDate, endDate, activityID));
    }


    public static ArrayList<Project> getProjects() {
        return projects;
    }

    public static ArrayList<Activity> getActivities() {return activities; }

    public static void assignProjectLeader(Project project, Employee employee) {
        project.setProjectLeader(employee);
    }
}
