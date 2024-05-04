package controllers;

import model.Activity;
import model.Employee;
import model.Project;


import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;

import java.util.*;


import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;



import java.util.List;
import java.util.Locale;

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
        for (int i = 0; i < 100; i++) {
            employees.add(new Employee("Abe " + i));
        }
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

    public static void createActivity(String activityName, double totalTime, LocalDate startDate, LocalDate endDate, String projectName, int activityID) throws OperationNotAllowedException {
        checkActivityDetails(activityName, startDate, endDate, projectName);
        Activity activity = new Activity(activityName, totalTime, startDate, endDate, activityID);
        activities.add(activity);
        addActivityToProject(getProject(projectName), activityID);
    }
    public static void createActivity(String activityName, double totalTime, LocalDate startDate, LocalDate endDate, String projectName) throws OperationNotAllowedException {
        checkActivityDetails(activityName, startDate, endDate, projectName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        String start = startDate.format(formatter);
        String end = endDate.format(formatter);
        String baseId = activityName + totalTime + start + end;
        int activityID = Math.abs(baseId.hashCode());
        Activity activity = new Activity(activityName, totalTime, startDate, endDate, activityID);
        activities.add(activity);
        addActivityToProject(getProject(projectName), activityID);
    }
    public static void createActivity(Activity activity, String projectName){
        activities.add(activity);
        addActivityToProject(getProject(projectName), activity.getActivityID());
    }

    public static void checkActivityDetails(String activityName, LocalDate startDate, LocalDate endDate, String projectName) throws OperationNotAllowedException {
        if (activityName.isEmpty()  || startDate == null || endDate == null || projectName.isEmpty()){
            throw new OperationNotAllowedException("Not all fields are filled out correctly");
        }
        if (startDate.isAfter(endDate)) {
            throw new OperationNotAllowedException("The start date is after the end date");
        }
    }

    public static void modifyActivity(int activityID, String activityName, double totalTime, LocalDate startDate, LocalDate endDate, String projectName, boolean completed) throws OperationNotAllowedException {
        checkActivityDetails(activityName, startDate, endDate, projectName);
        Activity activity = getActivity(activityID);
        activity.setActivityName(activityName);
        activity.setTotalTime(totalTime);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCompletion(completed);



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
    public static void addEmployeeToActivity(int activityID, Employee employee, Project project) throws OperationNotAllowedException {
        if (!getFreeEmployees(project, getActivity(activityID).getStartDate(), getActivity(activityID).getEndDate()).keySet().contains(employee)) {
            throw new OperationNotAllowedException("Employee not available");
        } else {

            for (Activity activity : activities) {
                if (activity.getActivityID() == activityID) {
                    if (activity.getEmployees().contains(employee)) {
                        throw new OperationNotAllowedException("Employee already assigned");
                    } else {
                        activity.addEmployee(employee);
                    }
                }
            }
        }
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

    public static Map<Employee, Integer> getFreeEmployees(Project project, LocalDate startDate, LocalDate endDate) throws OperationNotAllowedException {
        List<Activity> activities = getActivitiesFromProject(project);
        Map<Employee, Integer> overlapCounts = new HashMap<Employee, Integer>();
        Map<Employee, Integer> freeEmployeeMap;
        List<Employee> employeeList = new ArrayList<>(getEmployees().stream().filter(e -> e.getPersonalActivities().isEmpty()).toList());

        for (Employee employee : employeeList){
            overlapCounts.put(employee, 0);
        }

        if (activities.isEmpty()){
            throw new OperationNotAllowedException("No activities exist");
        }

        for (Activity activity : activities){
            if (startDate.compareTo(activity.getEndDate()) <= 0 && endDate.compareTo(activity.getStartDate()) >= 0){
                for (Employee employee : activity.getEmployees()){
                    if (employeeList.contains(employee)){
                        overlapCounts.put(employee, overlapCounts.get(employee) + 1);
                    }
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
    public double getCompletionsStatus(Project projectName){

        if (projectName.getActivityIDs().size() == 0){
            return 0;
        }

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusWeeks(1);
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int startWeekNumber = startDate.get(woy);
        int endWeekNumber = endDate.get(woy);
        double sum = 0;
        for (int id : projectName.getActivityIDs()){
            sum += getActivity(id).getCompletionStatus(startWeekNumber, endWeekNumber, woy);
        }

        return sum / projectName.activityIDs.size();
    }

    public double actualTimeSpentOnActivity(int activityID) throws OperationNotAllowedException {
        if ( activities.stream().noneMatch(p -> p.getActivityID() == activityID) ){
            throw new OperationNotAllowedException("Activity doesn't exist");
        }
        double amount = 0;
        for (Employee employee : employees){
            if ( employee.getTimeUsed(activityID) < 0 ){
                throw new OperationNotAllowedException("Can't have negative time spent");
            }
            amount += employee.getTimeUsed(activityID);
        }
        return amount;
    }

    public void registerTime(int activityID, String initials, double time) throws OperationNotAllowedException {
        if ( time < 0 ){
            throw new OperationNotAllowedException("Can't have negative time spent");
        }
        getEmployee(initials).registerTime(activityID, time);
    }
    public void addPersonalActivity(String initials, LocalDate start, LocalDate end, String reason) throws OperationNotAllowedException {
        getEmployee(initials).addPersonalActivity(start, end, reason);
    }
}
