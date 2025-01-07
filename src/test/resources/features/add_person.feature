Feature: Add Person
  As a user
  I want to add a new person
  So that I can store their information in the system

  Scenario: Successfully add a new person
    Given I have person details to add
      | firstName | lastName | email           | age |
      | John     | Doe      | john@email.com  | 30  |
    When I send a request to add the person
    Then the person should be successfully created
    And the response should contain the person details 