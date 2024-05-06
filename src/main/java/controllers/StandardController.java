package controllers;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.DataModel;

import java.util.function.UnaryOperator;

//Mob programming
public abstract class StandardController {
    DataModel model ;
    ProPlannerPlus proPlannerPlus;
    TextFormatter doubleTextFormatter;


    public void initController(DataModel model, ProPlannerPlus proPlannerPlus) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model ;

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
