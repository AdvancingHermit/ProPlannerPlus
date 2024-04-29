Feature: An employee creates a project
  Actor: Employee

  Scenario: An employee creates a project
    Given The name of the project
    When the employee creates a project
    Then a project is created

  Scenario: assign project leader to project
    Given a project exists
    And an employee exists
    When employee is assigned as project leader
    Then employee is the project leader