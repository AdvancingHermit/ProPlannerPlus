Feature: Manage Employees
  Description: Add or remove employees, from the list of employees
  Actors: Employees


  Scenario: Add employee
    Given an employee wants to add a new employee
    When the employee adds the new employee
    Then the new employee is added to employees.

  Scenario: Add already existing employee
    Given an employee who adds an existing employee
    When the employee adds the employee
    Then Then An error message "Employee already exist" is given

  Scenario: Remove existing employee
    Given an employee wants to remove an employee
    When the employee wants to remove the employee
    Then the employee is removed from employees.

  Scenario: Remove non-existing employee
    Given an employee who tries to remove a non existing employee
    When the employee tries to remove the employee
    Then An error message "Employee does not exist" is given
