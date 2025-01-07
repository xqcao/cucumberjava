# Person API Cucumber Tests

This guide explains how to set up Cucumber tests for the Person API's add person functionality.

## Required Files

📦 src/
┣ 📂 test/
┃ ┣ 📂 java/
┃ ┃ ┗ 📂 com/clinecode/onecline/
┃ ┃ ┃ ┣ 📂 config/
┃ ┃ ┃ ┃ ┗ 📄 CucumberTestConfig.java
┃ ┃ ┃ ┣ 📂 stepdefs/
┃ ┃ ┃ ┃ ┗ 📄 AddPersonSteps.java
┃ ┃ ┃ ┣ 📄 CucumberRunnerTest.java
┃ ┃ ┃ ┗ 📄 OneclineApplicationTests.java
┃ ┗ 📂 resources/
┃ ┃ ┗ 📂 features/
┃ ┃ ┃ ┗ 📄 add_person.feature




## Setup

### 1. Maven Dependencies
Add the following to your `pom.xml`:
<dependency>
<groupId>io.cucumber</groupId>
<artifactId>cucumber-java</artifactId>
<version>7.15.0</version>
<scope>test</scope>
</dependency>
<dependency>
<groupId>io.cucumber</groupId>
<artifactId>cucumber-spring</artifactId>
<version>7.15.0</version>
<scope>test</scope>
</dependency>
<dependency>
<groupId>io.cucumber</groupId>
<artifactId>cucumber-junit-platform-engine</artifactId>
<version>7.14.0</version>
<scope>test</scope>
</dependency>
<dependency>
<groupId>org.junit.platform</groupId>
<artifactId>junit-platform-suite</artifactId>
<scope>test</scope>
</dependency>

### 2. Test Files

#### Feature File
Create `add_person.feature`:
gherkin

Feature: Add Person
As a user
I want to add a new person
So that I can store their information in the system
Scenario: Successfully add a new person
Given I have person details to add
| firstName | lastName | email | age |
| John | Doe | john@email.com | 30 |
When I send a request to add the person
Then the person should be successfully created
And the response should contain the person details

#### Step Definitions
Create `AddPersonSteps.java`:

java
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
java
@CucumberContextConfiguration
@SpringBootTest(classes = OneclineApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberTestConfig {
}
java
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.clinecode.onecline.stepdefs,com.clinecode.onecline.config")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class CucumberRunnerTest {
}
bash
mvn clean test


The test results will be displayed in the console and HTML reports will be generated in the `target/cucumber-reports` directory.

### Test Reports
- Console output: Shows test execution progress
- HTML reports: Generated in `target/cucumber-reports/`
- Surefire reports: Generated in `target/surefire-reports/`

## Test Flow
1. Prepare test data (person details)
2. Send POST request to `/api/persons`
3. Verify response status code (200 OK)
4. Verify response body contains correct person details

## Files Modified
- ✨ `pom.xml` - Added Cucumber dependencies
- 📝 `add_person.feature` - Created feature file
- 🔧 `AddPersonSteps.java` - Implemented step definitions
- ⚙️ `CucumberTestConfig.java` - Added Spring configuration
- 🏃 `CucumberRunnerTest.java` - Created test runner