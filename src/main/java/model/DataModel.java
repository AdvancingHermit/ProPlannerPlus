package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DataModel {
    
    private final ObjectProperty<Employee> currentEmployee = new SimpleObjectProperty<>(null);

    public ObjectProperty<Employee> currentPersonProperty() {
        return currentEmployee ;
    }

    public final Employee getCurrentEmployee() {
        return currentEmployee.get();
    }
    public final void setCurrentEmployee(Employee person) {
        currentEmployee.set(person);
    }

}