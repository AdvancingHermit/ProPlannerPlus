package DesignByContract;

import static org.junit.jupiter.api.Assertions.*;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import model.Activity;
import model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class ActualTimeDBC {

    private ProPlannerPlus proPlannerPlus;
    private Employee employee1;

    @BeforeEach
    void setUp() {
        proPlannerPlus = new ProPlannerPlus();
    }

    @Test
    void testNullActivities() {
        proPlannerPlus.setActivities(null);
        AssertionError assertionError = assertThrows(AssertionError.class, () ->  proPlannerPlus.actualTimeSpentOnActivity(0));
        Assertions.assertEquals("Activities list cannot be null", assertionError.getMessage());
    }

    @Test
    void testNullEmployees() {
        proPlannerPlus.setEmployees(null);
        AssertionError assertionError = assertThrows(AssertionError.class, () -> proPlannerPlus.actualTimeSpentOnActivity(0));
        Assertions.assertEquals("Employees list cannot be null", assertionError.getMessage());
    }
    @Test
    void negativeTimeSpent() {

        AssertionError assertionError = assertThrows(AssertionError.class, () -> proPlannerPlus.actualTimeSpentOnActivity(0));
        Assertions.assertEquals("Employees list cannot be null", assertionError.getMessage());
    }
}
