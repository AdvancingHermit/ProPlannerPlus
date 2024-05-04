package junit;

import controllers.OperationNotAllowedException;
import controllers.ProPlannerPlus;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class checkActivityDetailsTest {

    ProPlannerPlus proPlannerPlus;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @org.junit.Before // Junit 4
    public void setUp() {
        proPlannerPlus = new ProPlannerPlus();
    }

    @org.junit.Test // JUnit 4
    public void allFieldsFilled() throws OperationNotAllowedException {
        String activityName = "test";
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 2);
        String projectName = "testProject";
        assertDoesNotThrow(() -> proPlannerPlus.checkActivityDetails(activityName, startDate, endDate, projectName));
    }

    @org.junit.Test // JUnit 4
    public void startDateAfterEndDate() throws OperationNotAllowedException {
        String activityName = "test";
        LocalDate startDate = LocalDate.of(2024, 1, 2);
        LocalDate endDate = LocalDate.of(2024, 1, 1);
        String projectName = "testProject";
        expectedEx.expectMessage("The start date is after the end date");
        proPlannerPlus.checkActivityDetails(activityName, startDate, endDate, projectName);
    }

    @org.junit.Test // JUnit 4
    public void invalidFilledFields() throws OperationNotAllowedException {
        String activityName = "";
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 2);
        String projectName = "testProject";
        expectedEx.expectMessage("Not all fields are filled out correctly");
        proPlannerPlus.checkActivityDetails(activityName, startDate, endDate, projectName);
    }

}
