package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public interface EmployeeData {

    ObjectProperty<Employee> currentEmployee = new SimpleObjectProperty<>(null);
    Employee getCurrentEmployee();
    void setCurrentEmployee(Employee person);
}
