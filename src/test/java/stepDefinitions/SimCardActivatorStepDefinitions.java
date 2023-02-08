package stepDefinitions;

import au.Actuator.ActuationResult;
import au.Actuator.ActuatorRequest;
import au.DB_Connector.SimCardRepository;
import au.SimCard.SimCardInfo;
import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.spring.CucumberContextConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import io.cucumber.java.en.*;;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<String> response;
    private ActuatorRequest actuatorRequest;
    private String endPoint = "http://localhost:8444/actuate";
    private SimCardInfo simCard;
    private ActuationResult result;
    private SimCardInfo query;

    @Autowired
    private SimCardRepository simCardRepo;

    @Given("^the SIM Card Activator is up and running$")
    public void microservice_is_up_and_running() {
        // Make a request to the microservice to check if it is up and running
        response = restTemplate.getForEntity("http://localhost:8080/status", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @When("^I submit a request to activate the ICCID '1255789453849037777'$")
    public void submit_activation_request() {
        // Submit the activation request
        simCard = new SimCardInfo("1255789453849037777", "example@gmail.com", true);
        ActuationResult result = restTemplate.postForObject(endPoint, actuatorRequest, ActuationResult.class);
    }

    @Then("^I should receive a success response from the microservice$")
    public void receive_success_response() {
        // Check if the response from the microservice is success
        assertEquals(true, result.getSuccess());
    }

    @When("^I query the databse for the ICCID '1255789453849037777'$")
    public void query_database() {
        // Query the databse for the ICCID
        var query = restTemplate.getForObject("http://localhost:8080/query?simCardId={simCardId}", SimCardInfo.class, 1);
    }

    @Then("I should receive an is active response from the microservice")
    public void query_success() {
        assertTrue(this.query.isActive());
    }

    @When("^I submit a request to activate the ICCID '8944500102198304826'$")
    public void submit_activation_request_broken_sim() {
        // Submit the activation request
        simCard = new SimCardInfo("8944500102198304826", "example@gmail.com", true);
        ActuationResult result = restTemplate.postForObject(endPoint, actuatorRequest, ActuationResult.class);
    }

    @Then("^I should receive a success response from the microservice$")
    public void receive_success_response_broken() {
        // Check if the response from the microservice is success
        assertEquals(true, result.getSuccess());
    }

    @When("^I query the databse for the ICCID '8944500102198304826'$")
    public void query_database_broken() {
        // Query the databse for the ICCID
        var query = restTemplate.getForObject("http://localhost:8080/query?simCardId={simCardId}", SimCardInfo.class, 1);
    }

    @Then("I should receive an is not active response from the microservice")
    public void query_not_active() {
        assertFalse(this.query.isActive());
    }



}