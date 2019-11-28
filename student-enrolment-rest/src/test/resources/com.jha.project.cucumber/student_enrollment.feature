@studentenroll
Feature: Student creation and search

  Scenario: Student creation and search
    Given Student with proper parameters
      | id          | 223445 |
      | firstName   | Mike |
      | lastName    | Wong  |
      | class       | 3 A     |
      | nationality | Singapore |
    When I submit a "POST" request to "/enroll/add"
    Then student is saved in the database
    When I submit a "GET" request to "/enroll/get/223445"
    Then I get 1 student record
