package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity {

    String activityName;
    int hoursPerWeek;
    Date startDate;
    Date endDate;
    int activityID;

    public Activity(String activityName, int hoursPerWeek, Date startDate, Date endDate, int activityID){
        this.activityName = activityName;
        this.hoursPerWeek = hoursPerWeek;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activityID = activityID;
    }

    public String getName(){
        return activityName;
    }


}
