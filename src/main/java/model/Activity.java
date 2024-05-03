package model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Activity {
    ArrayList<Employee> employees = new ArrayList<Employee>();
    String activityName;
    float hoursPerWeek;
    LocalDate startDate;
    LocalDate endDate;
    int activityID;

    public Activity(String activityName, float hoursPerWeek, LocalDate startDate, LocalDate endDate, int activityID){
        this.activityName = activityName;
        this.hoursPerWeek = hoursPerWeek;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activityID = activityID;
    }

    public String getName(){
        return activityName;
    }
    public void addEmployee(Employee employee){
        employees.add(employee);}
    public ArrayList<Employee> getEmployees(){return employees;}

    public int getActivityID() {
        return activityID;
    }
}
