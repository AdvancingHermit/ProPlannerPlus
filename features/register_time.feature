Feature: Register Time
  Description: The employee registers time
  Actors: Employee

  Scenario: Report Work Activity for half hours
    Given an activity exists
    When an employee registers a 1.5 hours for the activity
    Then 1.5 hours of time has been registered for the activity for employee

  Scenario: Report Personal Activity
    When an employee registers time on the personal activity
    Then that amount of time has been registered for the personal activity for employee