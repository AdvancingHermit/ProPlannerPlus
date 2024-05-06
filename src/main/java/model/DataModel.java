package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

//Made with mob programming
public class DataModel {

    Project currProject;

    public void setCurrProject(Project currProject) {
        this.currProject = currProject;
    }

    public Project getCurrProject() {
        return currProject;
    }

    private final ObjectProperty<Employee> currentEmployee = new SimpleObjectProperty<>(null);
    public final Employee getCurrentEmployee() {
        return currentEmployee.get();
    }
    public final void setCurrentEmployee(Employee person) {
        currentEmployee.set(person);
    }

}