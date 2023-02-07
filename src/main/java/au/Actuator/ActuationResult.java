package au.Actuator;

public class ActuationResult {

	private boolean success;

	public ActuationResult(boolean success) {
		this.success = success;
	}

	public boolean getSuccess() { return this.success; }

	public void setSuccess(boolean success) { this.success = success; }

}
