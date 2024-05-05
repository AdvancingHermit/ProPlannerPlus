package controllers;

import model.Activity;
import model.Employee;
import model.Project;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;

import java.util.*;

import java.util.ArrayList;

import java.util.List;

import java.util.stream.Collectors;

public class ProPlannerPlus {
    static ArrayList<Employee> employees;
    static ArrayList<Project> projects;
    static ArrayList<Activity> activities;
    public static boolean loggedIn = false;

    public static boolean adminLoggedIn = false;

    public ProPlannerPlus() {
        employees = new ArrayList<Employee>();
        employees.add(new Employee("abe"));
        projects = new ArrayList<Project>();
        activities = new ArrayList<Activity>();
        for (int i = 0; i < 100; i++) {
            employees.add(new Employee("Abe " + i));
        }
    }

    public static void setActivities(ArrayList<Activity> activities) {
        ProPlannerPlus.activities = activities;
    }

    public static void setEmployees(ArrayList<Employee> employees) {
        ProPlannerPlus.employees = employees;
    }

    public static void clearEmployees() {
        employees.clear();
    }

    public static ArrayList<Employee> getEmployees() {
        return employees;
    }

    public static void addEmployee(Employee employee) throws OperationNotAllowedException {
        if (employee.getInitials().length()== 0 ||employee.getInitials().length()>4){
            throw new OperationNotAllowedException("Initials can only be 4 letters long");
        }
        if (!employees.stream().filter(e -> e.getInitials().equals(employee.getInitials())).toList().isEmpty()) {
            throw new OperationNotAllowedException("Employee already exist");
        }
        employees.add(employee);
    }

    public static void removeEmployee(String initials) throws OperationNotAllowedException {
        List<String> employeeInitials = ProPlannerPlus.getEmployees().stream().map(Employee::getInitials).toList();
        if (!employeeInitials.contains(initials)) {
            throw new OperationNotAllowedException("Employee does not exist");
        }
        employees.remove(getEmployees().stream().filter(e -> e.getInitials().equals(initials)).toList().get(0));

    }

    public static boolean login(String initials) {
        if (initials.equals("admin")){
            adminLoggedIn = true;
            return true;
        }
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

    public static void createActivity(String activityName, double totalTime, LocalDate startDate, LocalDate endDate,
            String projectName, int activityID) throws OperationNotAllowedException {
        checkActivityDetails(activityName, startDate, endDate, projectName);
        Activity activity = new Activity(activityName, totalTime, startDate, endDate, activityID);
        activities.add(activity);
        addActivityToProject(getProject(projectName), activityID);
    }

    public static void createActivity(String activityName, double totalTime, LocalDate startDate, LocalDate endDate,
            String projectName) throws OperationNotAllowedException {
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

    public static void createActivity(Activity activity, String projectName) {
        activities.add(activity);
        addActivityToProject(getProject(projectName), activity.getActivityID());
    }

    public static void checkActivityDetails(String activityName, LocalDate startDate, LocalDate endDate,
            String projectName) throws OperationNotAllowedException {
        if (activityName.isEmpty() || startDate == null || endDate == null || projectName.isEmpty()) {
            throw new OperationNotAllowedException("Not all fields are filled out correctly");
        }
        if (startDate.isAfter(endDate)) {
            throw new OperationNotAllowedException("The start date is after the end date");
        }
    }

    public static void modifyActivity(int activityID, String activityName, double totalTime, LocalDate startDate,
            LocalDate endDate, String projectName, boolean completed) throws OperationNotAllowedException {
        checkActivityDetails(activityName, startDate, endDate, projectName);
        Activity activity = getActivity(activityID);
        activity.setActivityName(activityName);
        activity.setTotalTime(totalTime);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCompletion(completed);

    }

    public static void addActivityToProject(Project project, int activityID) {
        project.addActivity(activityID);
    }

    public static ArrayList<Project> getProjects() {
        return projects;
    }

    public static Project getProject(String name) {
        for (Project project : projects) {
            if (project.getName().equals(name)) {
                return project;
            }
        }
        return null;
    }

    public static Employee getEmployee(String initials) {
        for (Employee employee : employees) {
            if (employee.getInitials().equals(initials)) {
                return employee;
            }
        }
        return null;
    }

    public static void addEmployeeDirectlyToActivity(int activityID, Employee employee) {
        for (Activity activity : activities) {
            if (activity.getActivityID() == activityID) {
                activity.addEmployee(employee);
            }

        }
    }

    public static void addEmployeeToActivity(int activityID, Employee employee) throws OperationNotAllowedException {
        if (!getFreeEmployees(getActivity(activityID).getStartDate(), getActivity(activityID).getEndDate()).keySet()
                .contains(employee)) {
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


    public static Map<Employee, Integer> getFreeEmployees(LocalDate startDate, LocalDate endDate)
            throws OperationNotAllowedException {
        // Preconditions
        assert !startDate.isAfter(endDate) : "Start date can not be after end date";
        assert activities != null : "Activities list cannot be null";
        assert employees != null : "Employees list cannot be null";

        Map<Employee, Integer> overlapCounts = new HashMap<Employee, Integer>();
        Map<Employee, Integer> freeEmployeeMap;
        List<Employee> employeeList = new ArrayList<>(getEmployees().stream().filter(e -> e.getPersonalActivities()
                .isEmpty()).toList());

        for (Employee employee : employeeList) { // 1
            overlapCounts.put(employee, 0);
        }
        if (activities.isEmpty()) { // 2
            throw new OperationNotAllowedException("No activities exist"); // 3
        }
        for (Activity activity : activities) { // 4
            if (startDate.compareTo(activity.getEndDate()) <= 0 && endDate.compareTo(activity.getStartDate()) >= 0) {// 5
                for (Employee employee : activity.getEmployees()) { // 6
                    if (employeeList.contains(employee)) { // 7
                        overlapCounts.put(employee, overlapCounts.get(employee) + 1);
                    }
                }
            }
        }

        freeEmployeeMap = overlapCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        //Postconditions
        assert freeEmployeeMap.getClass().equals(LinkedHashMap.class);
        return freeEmployeeMap;
    }

    public static ArrayList<Activity> getActivities() {
        return activities;
    }

    public static void assignProjectLeader(Project project, Employee employee) {
        project.setProjectLeader(employee);
    }

    public double getCompletionStatus(Project project) throws NullPointerException {

        if (project.getActivityIDs().size() == 0) {                            // 1
            throw new NullPointerException("No activities exists in project"); // 2
        }
        double sumTotal = 0;
        double sumUsed = 0;

        for (int id : project.getActivityIDs()){                                //3
            if(getActivity(id).getComplete()) {                                 //4
                for (Employee employee : getEmployees()) {                      //5
                    sumTotal += employee.getTimeUsed(id);
                    sumUsed += employee.getTimeUsed(id);
                }
            } else {
                sumTotal += getActivity(id).getTotalTime();
            }
        }
        if (sumUsed == 0) {                                                     //6
            return 0;                                                           //7
        }
        return sumUsed / sumTotal;                                              //8
    }

    public double actualTimeSpentOnActivity(int activityID) throws OperationNotAllowedException {
        // Preconditions
        assert activityID >= 0 : "Activity ID must be non-negative";
        assert activities != null : "Activities list cannot be null";
        assert employees != null : "Employees list cannot be null";

        if (activities.stream().noneMatch(p -> p.getActivityID() == activityID)) {
            throw new OperationNotAllowedException("Activity doesn't exist");
        }
        double amount = 0;
        for (Employee employee : employees) {
            if (employee.getTimeUsed(activityID) < 0) {
                throw new OperationNotAllowedException("Can't have negative time spent");
            }
            amount += employee.getTimeUsed(activityID);
        }
        // Postconditions
        assert amount >= 0 : "Total time spent cannot be negative";
        return amount;
    }

    public void registerTime(int activityID, String initials, double time) throws OperationNotAllowedException {
        if (time < 0) {
            throw new OperationNotAllowedException("Can't have negative time spent");
        }
        getEmployee(initials).registerTime(activityID, time);
    }

    public void addPersonalActivity(String initials, LocalDate start, LocalDate end, String reason)
            throws OperationNotAllowedException {
        getEmployee(initials).addPersonalActivity(start, end, reason);
    }
}
