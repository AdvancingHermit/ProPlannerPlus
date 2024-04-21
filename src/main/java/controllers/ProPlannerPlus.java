package controllers;

import model.Employee;

import java.util.ArrayList;

public class ProPlannerPlus {
    static ArrayList<Employee> employees;
    public static boolean loggedIn = false;

    public ProPlannerPlus() {
        employees = new ArrayList<Employee>();
        employees.add(new Employee("abe"));
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
}
