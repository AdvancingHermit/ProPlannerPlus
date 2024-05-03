package controllers;

import model.Activity;
import model.Employee;
import model.Project;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.Date;

import java.util.List;

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

    public static void addEmployee(Employee employee) throws OperationNotAllowedException {
        if (!employees.stream().filter(e->e.getInitials().equals(employee.getInitials())).toList().isEmpty()) {
            throw new OperationNotAllowedException("Employee already exist");
        }
        employees.add(employee);
    }




    public static void removeEmployee(String initials) throws OperationNotAllowedException {
        List<String> employeeInitials = ProPlannerPlus.getEmployees().stream().map(Employee::getInitials).toList();
        if (!employeeInitials.contains(initials)){
            throw new OperationNotAllowedException("Employee does not exist");
        }
        employees.remove(getEmployees().stream().filter(e -> e.getInitials().equals(initials)).toList().get(0));

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

    public static void createActivity(String activityName, double hoursPerWeek, LocalDate startDate, LocalDate endDate, String projectName){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        String start = startDate.format(formatter);
        String end = endDate.format(formatter);
        System.out.println(start);
        String baseId = activityName + hoursPerWeek + start + end;
        int activityID = Math.abs(baseId.hashCode());
        Activity activity = new Activity(activityName, hoursPerWeek, startDate, endDate, activityID);
        activities.add(activity);
        addActivityToProject(getProject(projectName), activityID);
    }

    public static void addActivityToProject(Project project, int activityID){
        project.addActivity(activityID);
    }


    public static ArrayList<Project> getProjects() {
        return projects;
    }

    public static Project getProject(String name){
        for (Project project : projects){
            if (project.getName().equals(name)){
                return project;
            }
        }
        return null;
    }

    public static Employee getEmployee(String initials){
        for (Employee employee : employees){
            if (employee.getInitials().equals(initials)){
                return employee;
            }
        }
        return null;
    }

    public static ArrayList<Activity> getActivities() {return activities; }

    public static void assignProjectLeader(Project project, Employee employee) {
        project.setProjectLeader(employee);
    }
}
