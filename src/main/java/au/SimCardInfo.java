package au;

import javax.persistence.Entity;

@Entity
public class SimCardInfo {
	
	String ICCID;
	String email;

	public SimCardInfo(String ICCID, String email) {
		this.ICCID = ICCID;
		this.email = email;
	}

	public void setICCID(String ICCID) { this.ICCID = ICCID; }

	public void setEmail(String email) { this.email = email; }

	public String getICCID() { return this.ICCID; }

	public String getEmail() { return this.email; }

}
