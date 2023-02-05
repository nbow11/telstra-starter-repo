package au;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@RestController
public class SimController {
	
	@PostMapping("/activate")
	public void activateSimInfo(@RequestBody SimCardInfo cardInfo) {

		String endPoint = "http://localhost:8444/actuate";
		
		// get details from the request JSON payload
		String ICCID = cardInfo.getICCID(), email = cardInfo.getEmail();

		// prepare actuator request and send ICCID
		ActuatorRequest actuatorRequest = new ActuatorRequest(ICCID);

		// post ICCID to actuator
		RestTemplate restTemplate = new RestTemplate();
		ActuationResult result = restTemplate.postForObject(endPoint, actuatorRequest, ActuationResult.class);
		
		// get response from actuator whether success or not
		if (result.getSuccess()) {
			System.out.println("Activating the sim card was a success!");
		} else {
			System.out.println("Activating the sim failed");
		}

	}
}
