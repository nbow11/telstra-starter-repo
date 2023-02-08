package stepDefinitions;

import au.Actuator.ActuationResult;
import au.Actuator.ActuatorRequest;
import au.DB_Connector.SimCardRepository;
import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.spring.CucumberContextConfiguration;

import static org.junit.Assert.assertEquals;

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

    @Autowired
    private SimCardRepository simCardRepo;

    @Given("^the SIM Card Activator is up and running$")
    public void microservice_is_up_and_running() {
        // Make a request to the microservice to check if it is up and running
        response = restTemplate.getForEntity("http://localhost:8080/status", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @When("^I submit a request to activate the ICCID '1255789453849037777'$")
    public void submit_activation_request(String iccid) {
        // Submit the activation request
        actuatorRequest = new ActuatorRequest(iccid);
    }

    @Then("^I should receive a success response from the microservice$")
    public void receive_success_response() {
        // Check if the response from the microservice is success
        ActuationResult result = restTemplate.postForObject(endPoint, actuatorRequest, ActuationResult.class);
        assertEquals(true, result.getSuccess());
    }

    @When("^I query the databse for the ICCID '1255789453849037777'$")
    public void query_microservice_status(String iccid) {
        // Query the databse for the ICCID
        response = restTemplate.getForEntity("http://localhost:8080/status/" + iccid, String.class);
    }

    @Then("^I should receive a response indicating that the SIM card has been activated successfully$")
    public void receive_activated_response() {
        // Check if the response from the microservice indicates that the SIM card has been activated
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ACTIVATED", response.getBody());
    }

}