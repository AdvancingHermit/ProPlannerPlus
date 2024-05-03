package controllers;

import model.Activity;
import model.Employee;
import model.Project;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.stream.Collectors;

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
    public static void createActivity(Activity activity, String projectName){
        activities.add(activity);
        addActivityToProject(getProject(projectName), activity.getActivityID());
    }

    public static void addActivityToProject(Project project, int activityID){
        project.addActivity(activityID);
    }


    public static ArrayList<Project> getProjects() {
        return projects;
    }

    public static Project getProject(String name) {
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
    public static void addEmployeeToActivity(String activityName, Employee employee) throws OperationNotAllowedException {
        // if (!employeeAvaviable){
        // throw new OperationNotAllowedException("Employee not available");
        // } else {

        for (Activity activity : activities) {
            if (activity.getName().equals(activityName)){
                if (activity.getEmployees().contains(employee)){
                    throw new OperationNotAllowedException("Employee already assigned");
                } else {
                    activity.addEmployee(employee);
                }
            }
        }
    }
    public static Activity getActivity(String name) {
        for (Activity activity : activities) {
            if (activity.getName().equals(name)) {
                return activity;
            }
        }
        return null;
    }

    public static Activity getActivity(int id) {
        for (Activity activity : activities) {
            if (activity.getActivityID() == id) {
                return activity;
            }
        }
        return null;
    }

    public static List<Activity> getActivitiesFromProject(Project project){
        List<Activity> activities = new ArrayList<>();
        for (int activityID : project.getActivityIDs()){
            activities.add(getActivity(activityID));
        }
        return activities;
    }

    public static Map<Employee, Integer> getFreeEmployees(Project project, LocalDate startDate, LocalDate endDate){
        List<Activity> activities = getActivitiesFromProject(project);
        Map<Employee, Integer> overlapCounts = new HashMap<Employee, Integer>();
        Map<Employee, Integer> freeEmployeeMap;
        List<Employee> employeeList = new ArrayList<>(getEmployees().stream().filter(e -> e.getPersonalActivities().isEmpty()).toList());

        for (Activity activity : activities){
            if ((startDate.isBefore(activity.getEndDate()) || startDate.isEqual(activity.getEndDate())) && (endDate.isAfter(activity.getStartDate()) || endDate.isEqual(activity.getStartDate()))){
                for (Employee employee : activity.getEmployees()){
                    if (employeeList.contains(employee)){
                        overlapCounts.put(employee, overlapCounts.getOrDefault(employee, 0) + 1);
                    }
                }
            } else {
                for (Employee employee : employeeList){
                    overlapCounts.put(employee, overlapCounts.getOrDefault(employee, 0));
                }
            }
        }
        freeEmployeeMap = overlapCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return freeEmployeeMap;
    }


    public static ArrayList<Activity> getActivities() {return activities; }

    public static void assignProjectLeader(Project project, Employee employee) {
        project.setProjectLeader(employee);
    }
}
