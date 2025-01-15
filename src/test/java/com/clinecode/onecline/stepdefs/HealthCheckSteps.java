package com.clinecode.onecline.stepdefs;

import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class HealthCheckSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<Map> response;

    @When("I send a request to health check endpoint")
    public void i_send_a_request_to_health_check_endpoint() {
        response = restTemplate.getForEntity("/actuator/health", Map.class);
    }

    @Then("the application should be up")
    public void the_application_should_be_up() {
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("UP", response.getBody().get("status"));
    }

    @And("the response should include health details")
    public void the_response_should_include_health_details() {
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.containsKey("status"));
    }
}