package au.SimCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

import au.Actuator.ActuationResult;
import au.Actuator.ActuatorRequest;
import au.DB_Connector.SimCardRepository;

@RestController
public class SimController {

	@Autowired
	SimCardRepository simCardRepo;

	@PostMapping("/activate")
	public void activateSimInfo(@RequestBody SimCardInfo cardInfo) {

		String endPoint = "http://localhost:8444/actuate";
		
		// get details from the request JSON payload
		String ICCID = cardInfo.getICCID(), email = cardInfo.getEmail();
		boolean activeStatus = cardInfo.isActive();

		// prepare actuator request and send ICCID
		ActuatorRequest actuatorRequest = new ActuatorRequest(ICCID);

		// post ICCID to actuator
		RestTemplate restTemplate = new RestTemplate();
		ActuationResult result = restTemplate.postForObject(endPoint, actuatorRequest, ActuationResult.class);
		
		// get response from actuator whether success or not and save to DB
		if (result.getSuccess()) {
			System.out.println("Activating the sim card was a success!");

			// save card to database
			simCardRepo.save(new SimCardInfo(ICCID, email, activeStatus));
		} else {
			System.out.println("Activating the sim failed");
		}

	}

	// set up query endpoint
	@GetMapping("/query")
	public ResponseEntity<SimCardInfo> endPointQuery(@PathVariable Long simCardId) {
		var matchingSim = simCardRepo.findById(simCardId);
		
		// return if simCardId is present
		if (matchingSim.isPresent()) {
			return new ResponseEntity<>(matchingSim.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
