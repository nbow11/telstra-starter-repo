package au;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class SimController {
	
	@PostMapping("/activate")
	public void activateSimInfo(@RequestBody SimCardInfo cardInfo) {

		String endPoint = "http://localhost:8444/actuate";
		
		// get details from the request JSON payload
		String ICCID = cardInfo.getICCID(), email = cardInfo.getEmail();

		// prepare actuator request and send ICCID
		ActuatorRequest actuatorRequest = new ActuatorRequest(ICCID);

		

	}
}
