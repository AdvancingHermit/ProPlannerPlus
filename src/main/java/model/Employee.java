package model;

import controllers.OperationNotAllowedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Employee {
    String initials;
    HashMap<Integer, Double> schedule;
    ArrayList<PersonalActivity> personalActivities;


    public Employee(String initials){
        this.initials = initials;
        this.schedule = new HashMap<Integer, Double>();
        personalActivities = new ArrayList<PersonalActivity>();
    }

    public String getInitials(){
        return initials;
    }

    public void registerTime(int activityID, double time) {
        double currTemp = 0;

        if (schedule.containsKey(activityID)){
            currTemp = schedule.get(activityID);
        }

        schedule.put(activityID, currTemp + time);
    }

    public void addPersonalActivity(LocalDate start, LocalDate end, String reason) throws OperationNotAllowedException {
        if (start == null || end == null || start.isAfter(end)){
            throw new OperationNotAllowedException("Please input valid dates");
        }
        personalActivities.add(new PersonalActivity(start, end, reason));
    }

    public double getTimeUsed(int activityID){
        return schedule.get(activityID);
    }

    public ArrayList<PersonalActivity> getPersonalActivities() {
        return personalActivities;
    }

    public String toString(){
        return initials;
    }
}

