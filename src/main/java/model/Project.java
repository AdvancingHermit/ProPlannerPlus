package model;

import java.util.ArrayList;
import java.util.List;


public class Project {

    private int id;
    private String name;
    private Employee projectLeader;

    public List<Integer> activityIDs;

    public Project(String name, int id) {
        this.name = name;
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


    public String toString(){
        return name;
    }
}
