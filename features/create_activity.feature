Feature: An employee creates an activity
  Actor: Employee

  Scenario: An employee creates an activity
    Given the info of an activity
    When the employee creates a activity
    Then an activity is created

  Scenario: An employee tries to create and activity without all the info
    Given missing info for the activity
    When the employee creates a activity
    Then the error message "Not all fields are filled out correctly" is given

  Scenario: An employee tries to create and activity with end date before start date
    Given the end date is before the start date
    When the employee creates a activity
    Then the error message "The start date is after the end date" is given

  Scenario: modify activity
    Given an activity in the project
    When the employee modifies the activity
    Then the activity is now modified