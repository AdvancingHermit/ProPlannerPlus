package model;

import java.util.ArrayList;

public class Project {

    private int id;
    private String name;
    private Employee projectLeader;

    public Project(String name, int id) {
        this.name = name;
        this.id = id;
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
}
