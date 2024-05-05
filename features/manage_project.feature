Feature: Manage project
  Description: The project can be managed to see the status of the project
  Actor: Employee

  Scenario: View Completion Status with activities
    Given an employee exist
    And  a project contains 2 activites
    And an activity with id 0 and 2 hours is completed is in the project
    And an uncompleted activity with id 1 and 2 hours expected time is in the project
    When the employee views completion status
    Then the status is 0.5

  Scenario: View Completion Status with activities
    Given an employee exist
    And  a project contains 1 activites
    And an activity with 2 hours is completed is in the project
    When the employee views completion status
    Then the status is 1.0

  Scenario: View Completion Status with activities
    Given an employee exist
    And  a project contains 1 activites
    And an uncompleted activity with 2 hours expected time is in the project
    When the employee views completion status
    Then the status is 0.0

  Scenario: View Completion Status without activities
    Given an employee exist
    And a project with no activities
    When the employee views completion status
    Then the error message "No activities exists in project" is given