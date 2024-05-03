package model;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Project {

    private int id;
    private String name;
    private Employee projectLeader;

    public List<Integer> activityIDs;

    public Project(String name, int id) {
        this.name = name;
        this.id = id;
        activityIDs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setProjectLeader(Employee employee) {
        projectLeader = employee;
    }
    public Employee getProjectLeader() {
        return projectLeader;
    }

    public List<Integer> getActivityIDs() {
        return activityIDs;
    }

    public void addActivity(int activityID) {
        this.activityIDs.add(activityID);
    }

<<<<<<< Updated upstream
    public String toString(){
        return name;
    }
=======
>>>>>>> Stashed changes
}
