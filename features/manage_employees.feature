Feature: Manage Employees
  Description: Add or remove employees, from the list of employees
  Actors: Employees

  Scenario: Add employee
    Given an employee wants to add a new employee
    When the employee adds the new employee
    Then the new employee is added to employees.

  Scenario: Remove employee
    Given an employee wants to remove a employee
    When the employee removes the employee
    Then the employee is removed from employees.