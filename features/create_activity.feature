Feature: An employee creates an activity
  Actor: Employee

  Scenario: An employee creates an activity
    Given the name of an activity
    And the length of the activity in hours
    And the start date
    And the end date
    Then the activity is created