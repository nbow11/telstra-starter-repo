package au;

public class ActuatorRequest {
	String ICCID;

	public ActuatorRequest(String ICCID) { this.ICCID = ICCID; }

	public void setICCID(String ICCID) { this.ICCID = ICCID; } 

	public String getICCID() { return this.ICCID; }
}
