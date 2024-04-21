Feature: Get Free Employees
  Description: Finds all employees, who are not sick or working on another activity.
  Actors: Project leader or employee.

  Scenario: Employee wants to see free employees
    Given an employee wants to add a employee to an activity.
    When the employee wants to see who is available.
    Then a list of free employees are given.