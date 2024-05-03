package model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Activity {

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

    public int getActivityID() {
        return activityID;
    }
}
