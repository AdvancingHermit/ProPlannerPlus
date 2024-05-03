Feature: Get Free Employees
  Description: Finds all employees, who are not sick or working on another activity.
  Actors: Project leader or employee.

  Scenario: Employee wants to see free employees
    Given at least 1 free employee exists
    When the employee requests a list of free employees.
    Then a list of free employees are given.