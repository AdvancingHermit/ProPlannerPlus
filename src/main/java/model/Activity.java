package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;
//Made by Christian with help from all others
public class Activity {
    ArrayList<Employee> employees = new ArrayList<Employee>();
    String activityName;
    double totalTime;
    LocalDate startDate;
    LocalDate endDate;
    int activityID;
    boolean complete = false;

    public Activity(String activityName, double totalTime, LocalDate startDate, LocalDate endDate, int activityID){
        this.activityName = activityName;
        this.totalTime = totalTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activityID = activityID;
    }

    public String getName(){
        return activityName;
    }
    public void addEmployee(Employee employee){
        employees.add(employee);}
    public void removeEmployee(Employee employee){
        employees.remove(employee);
    }
    public List<Employee> getEmployees(){return employees;}

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getActivityID() {
        return activityID;
    }
    public LocalDate getStartDate()  {
        return startDate;
    }
    public LocalDate getEndDate()  {
        return endDate;
    }

    public double getTotalTime(){ return (int)totalTime; }


    public void setCompletion(Boolean complete) {this.complete = complete;}

    public Boolean getComplete(){ return complete; }

    @Override
    public String toString() {
        return activityName;
    }
    
}
