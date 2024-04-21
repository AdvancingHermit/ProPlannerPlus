Feature: Login
  Description: The employee logs in to the program using their initials
  Actors: Employee

  Scenario: An employee can log in
    Given the employee is not logged in and the employee is registered with their initials
    When an employee logs in using their initials
    Then the employee successfully logs in

  Scenario: An employee provides wrong initials
    Given the employee is not logged in
    When an employee tries to log in with initials not in the system
    Then the employee fails to logs in
