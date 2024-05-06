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
        long count = projects.stream().filter(p -> p.getName().hashCode() == (name.hashCode())).count();
        String serialNumber = String.format("%04d", count + 1);
        int projectID = Integer.parseInt( projects.size() + "" + (Year.now().getValue() - 2000) + serialNumber);
        projects.add(new Project(name, projectID));

    }

    public static void createTestProject(Project project) {
        projects.add(project);

    }


    public static void createActivity(String activityName, double totalTime, LocalDate startDate, LocalDate endDate,
            Integer projectID, int activityID) throws OperationNotAllowedException {
        checkActivityDetails(activityName, startDate, endDate, projectID);
        Activity activity = new Activity(activityName, totalTime, startDate, endDate, activityID);
        activities.add(activity);

        addActivityToProject(getProject(projectID), activityID);
    }

    public static void createActivity(String activityName, double totalTime, LocalDate startDate, LocalDate endDate,
            Integer projectID) throws OperationNotAllowedException {
        checkActivityDetails(activityName, startDate, endDate, projectID);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        String start = startDate.format(formatter);
        String end = endDate.format(formatter);
        String baseId = activityName + totalTime + start + end;
        int activityID = Math.abs(baseId.hashCode());
        Activity activity = new Activity(activityName, totalTime, startDate, endDate, activityID);
        activities.add(activity);
        addActivityToProject(getProject(projectID), activityID);
    }

    public static void createActivity(Activity activity, int projectID) {
        activities.add(activity);
        addActivityToProject(getProject(projectID), activity.getActivityID());
    }

    public static void checkActivityDetails(String activityName, LocalDate startDate, LocalDate endDate,
            Integer projectID) throws OperationNotAllowedException {
        if (activityName.isEmpty() || startDate == null || endDate == null || projectID == null) {
            throw new OperationNotAllowedException("Not all fields are filled out correctly");
        }
        if (startDate.isAfter(endDate)) {
            throw new OperationNotAllowedException("The start date is after the end date");
        }
    }

    public static void modifyActivity(int activityID, String activityName, double totalTime, LocalDate startDate,
            LocalDate endDate, Integer projectID, boolean completed) throws OperationNotAllowedException {
       if (startDate.isAfter(endDate)){
            throw new OperationNotAllowedException("Invalid dates");
        }
        checkActivityDetails(activityName, startDate, endDate, projectID);
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

    public static Project getProject(int id) {
        for (Project project : projects) {
            if (project.getId() == id) {
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
        // Pre-condition
        assert getActivities().stream().anyMatch(p -> p.getActivityID() == activityID) : "No matching activity for the ID";
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
        // Post-condition
        assert getActivity(activityID).getEmployees().contains(employee);
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
        assert !activities.isEmpty() : "No activities exist";

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

    public double getCompletionStatus(Project project) throws OperationNotAllowedException {

        //Preconditions
        assert project != null : "Project cannot be null";
        assert project.getActivityIDs().size() != 0 : "No activities exists in project";


        if (project.getActivityIDs().size() == 0) {
            throw new OperationNotAllowedException("No activities exists in project");
        }
        double sumTotal = 0;
        double sumUsed = 0;

        for (int id : project.getActivityIDs()){
            if(getActivity(id).getComplete()) {
                for (Employee employee : getEmployees()) {
                    sumTotal += employee.getTimeUsed(id);
                    sumUsed += employee.getTimeUsed(id);
                }
            } else {
                sumTotal += getActivity(id).getTotalTime();
            }
        }
        if (sumTotal == 0) {
            return 1;
        }

        //post conditions
        assert sumUsed / sumTotal >= 0 && sumUsed / sumTotal <= 1;


        return sumUsed / sumTotal;
    }

    public double actualTimeSpentOnActivity(int activityID) throws OperationNotAllowedException {
        // Preconditions
        assert activities != null : "Activities list cannot be null";
        assert employees != null : "Employees list cannot be null";
        assert activities.stream().anyMatch(a -> a.getActivityID() == activityID) : "Activity doesn't exist";

        if ( activities.stream().noneMatch(a -> a.getActivityID() == activityID) ) {
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
        assert amount >= 0 : "Can't have negative time spent";

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
