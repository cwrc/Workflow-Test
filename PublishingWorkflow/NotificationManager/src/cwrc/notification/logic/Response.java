package cwrc.notification.logic;

// This class specifies and implements the structure of responses (as a part of notifications)
public class Response {
	
	private String value;
	private String action;
	
	public Response() {
	}
	
	public Response(String value, String action){
		this.value=value;
		this.action=action;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "Response [value=" + value + ", action=" + action + "]";
	}
	
}
