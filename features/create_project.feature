Feature: An employee creates a project
  Actor: Employee

  Scenario: An employee creates a project
    Given The name of the project
    And The software gets the current year
    Then a Project ID is created
    Then a project is created

  Scenario: An employee creates a project
    Given a project
    And an employee
    Then assign the employee as project leader