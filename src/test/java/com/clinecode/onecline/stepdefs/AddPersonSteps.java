package com.clinecode.onecline.stepdefs;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.clinecode.onecline.model.Person;
import java.util.List;
import java.util.Map;

public class AddPersonSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private Person personToAdd;
    private ResponseEntity<Person> response;

    @Given("I have person details to add")
    public void i_have_person_details_to_add(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        Map<String, String> data = rows.get(0);

        personToAdd = new Person();
        personToAdd.setFirstName(data.get("firstName"));
        personToAdd.setLastName(data.get("lastName"));
        personToAdd.setEmail(data.get("email"));
        personToAdd.setAge(Integer.parseInt(data.get("age")));
    }

    @When("I send a request to add the person")
    public void i_send_a_request_to_add_the_person() {
        response = restTemplate.postForEntity("/api/persons", personToAdd, Person.class);
    }

    @Then("the person should be successfully created")
    public void the_person_should_be_successfully_created() {
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @And("the response should contain the person details")
    public void the_response_should_contain_the_person_details() {
        Person createdPerson = response.getBody();
        assertNotNull(createdPerson.getId());
        assertEquals(personToAdd.getFirstName(), createdPerson.getFirstName());
        assertEquals(personToAdd.getLastName(), createdPerson.getLastName());
        assertEquals(personToAdd.getEmail(), createdPerson.getEmail());
        assertEquals(personToAdd.getAge(), createdPerson.getAge());
    }
}