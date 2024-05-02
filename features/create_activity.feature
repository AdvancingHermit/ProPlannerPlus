Feature: An employee creates an activity
  Actor: Employee

  Scenario: An employee creates an activity
    Given the info of an activity
    When the employee creates a activity
    Then an activity is created