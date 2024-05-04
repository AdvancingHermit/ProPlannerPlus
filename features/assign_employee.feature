Feature: Assign employee
  Description: The employees are assigned to a activity
  Actors: Employee

  Scenario: Assign free employee to activity
    Given A free employee which is not assigned to the activity
    When The employee is assigned to the activity
    Then The employee is now part of the activity

  Scenario: Assign non-free employee to activity
    Given An non-free employee which is not assigned to the activity
    When The employee is assigned to the activity
    Then An error message "Employee not available" is given

  Scenario: Assign an already assigned employee to activity
    Given A free employee which is assigned to the activity
    When The employee is assigned to the activity
    Then the error message "Employee already assigned" is given