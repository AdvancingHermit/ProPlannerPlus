package controllers;

import model.DataModel;

public abstract class StandardController {
    DataModel model ;
    ProPlannerPlus proPlannerPlus;

    public void initController(DataModel model, ProPlannerPlus proPlannerPlus) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model ;

        if (this.proPlannerPlus != null) {
            throw new IllegalStateException("ProPlannerPlus can only be initialized once");
        }
        this.proPlannerPlus = proPlannerPlus ;



    }

}
