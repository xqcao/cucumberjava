Feature: Health Check
  As a system administrator
  I want to check the application health
  So that I can monitor the system status

  Scenario: Application health check
    When I send a request to health check endpoint
    Then the application should be up
    And the response should include health details 