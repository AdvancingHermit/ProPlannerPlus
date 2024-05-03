Feature: Add Activity
  Description: Adds an activity to a project
  Actors: Employee


  Scenario: add activity
    Given an Employee a project, and an activity
    When the employee ads the activity to the project
    Then the activity is now under the project