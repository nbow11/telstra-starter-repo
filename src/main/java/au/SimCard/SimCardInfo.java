package au.SimCard;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SimCardInfo {
	
	@Id
  @GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	String ICCID;
	String email;
	boolean active;

	protected SimCardInfo() {}

	public SimCardInfo(String ICCID, String email, boolean active) {
		this.ICCID = ICCID;
		this.email = email;
		this.active = active;
	}

	public void setICCID(String ICCID) { this.ICCID = ICCID; }

	public void setEmail(String email) { this.email = email; }

	public void setActive(boolean active) { this.active = active; }

	public String getICCID() { return this.ICCID; }

	public String getEmail() { return this.email; }

	public boolean isActive() { return this.active; }

	public Long getID() { return this.id; }

}
