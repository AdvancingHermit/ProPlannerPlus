Feature: Manage project
  Description: The project can be managed to see the status of the project
  Actor: Employee

  Scenario: View time
    Given An employee exist
    And a project exist
    When The employee views time status for the project
    Then The current status of time for the project is shown
