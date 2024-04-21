Feature: Register Time
  Description: The employee registers time
  Actors: Employee

  Scenario: Report Work Activity for whole hours
    Given an activity exists
    When an employee registers a number of whole hours for the activity
    Then that number of hours of time has been registered for the activity for employee

  Scenario: Report Work Activity for half hours
    Given an activity exists
    When an employee registers a 1.5 hours for the activity
    Then 1.5 hours of time has been registered for the activity for employee

  Scenario: Report Personal Activity
    When an employee registers a number of whole hours for the personal activity
    Then that number of hours of time has been registered for the personal activity for employee
