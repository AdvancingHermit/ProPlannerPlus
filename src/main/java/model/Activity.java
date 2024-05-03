package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;

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
    public List<Employee> getEmployees(){return employees;}

    public int getActivityID() {
        return activityID;
    }
    public LocalDate getStartDate()  {
        return startDate;
    }
    public LocalDate getEndDate()  {
        return endDate;
    }


    public void setCompletion(Boolean complete) {this.complete = complete;}

    @Override
    public String toString() {
        return activityName;
    }

    public double getCompletionStatus(int startWeekNumber, int endWeekNumber, TemporalField woy) {
        int weekAmount;
        if (complete){
            return 100.0;
        }
        if (startDate.getYear() == endDate.getYear()
                && (startDate.get(woy) >= startWeekNumber && startDate.get(woy) <= endWeekNumber)
                || (endDate.get(woy) >= startWeekNumber && endDate.get(woy) <= endWeekNumber)){
            weekAmount = endDate.get(woy) - startDate.get(woy);
            double weeklyTime = totalTime /  weekAmount;
            return weeklyTime;
        } else if ((startDate.get(woy) >= startWeekNumber && startDate.get(woy) <= endWeekNumber)
                || (endDate.get(woy) >= startWeekNumber && endDate.get(woy) <= endWeekNumber)){
            weekAmount = (int) startDate.until(endDate, ChronoUnit.WEEKS);
            double weeklyTime = totalTime /  weekAmount;
            return weeklyTime;
        }
        return 0.0;
    }
}
