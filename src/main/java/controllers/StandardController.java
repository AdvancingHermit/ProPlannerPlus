package controllers;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.DoubleStringConverter;

import java.util.function.UnaryOperator;
import model.EmployeeData;
import model.ProjectData;

//Mob programming
public abstract class StandardController {
    EmployeeData employeeData;
    ProjectData projectData;
    ProPlannerPlus proPlannerPlus;
    TextFormatter doubleTextFormatter;


    public void initController(EmployeeData employeeData, ProjectData projectData, ProPlannerPlus proPlannerPlus) {
        if (this.employeeData != null && this.projectData != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.employeeData = employeeData;
        this.projectData = projectData;

        if (this.proPlannerPlus != null) {
            throw new IllegalStateException("ProPlannerPlus can only be initialized once");
        }
        this.proPlannerPlus = proPlannerPlus ;

        doubleTextFormatter = new TextFormatter<Double>(new DoubleStringConverter(), 0d, doubleFilter);

    }


    public double getNumber(String val){
        if (val.matches("([0-9]+[.])?[0-9]+")){
            return Double.parseDouble(val);
        }
        return -1;
    }
    UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("\\d+(\\.\\d*)?") || newText.isEmpty()) {
            return change;
        }
        return null;
    };


}
