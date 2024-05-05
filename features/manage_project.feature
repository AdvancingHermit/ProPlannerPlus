Feature: Manage project
  Description: The project can be managed to see the status of the project
  Actor: Employee

  Scenario: View Completion Status with activities
    Given an employee exist
    And  a project has activites
    When the employee views completion status
    Then the completion status is the percentage of completed activities in the project

  Scenario: View Completion Status without activities
    Given an employee exists
    And a project has no activites
    When the employee views completion status
    Then the error message "No activities exists in project" is given
