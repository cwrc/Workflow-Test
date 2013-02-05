package cwrc.notification.logic;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;


// This class specifies and impelements the structure of notifications
public class Notification {

	private String id;
	private String sender;
	private String receiver;
	private String subject;
	private String description;
	private String url;
	private Date time;
	private boolean checked;
	private Response[] responses;
	
	public Notification() {
		responses = new Response[0];
	}
	
	public Notification(String id, String sender, String receiver, String subject,
			String description, String url, Timestamp time, boolean active, Response[] responses) {
		
		this.id=id;
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.description = description;
		this.url = url;
		this.time = time;
		this.checked = active;
		this.responses = responses;
	}

	public Notification(String sender, String receiver, String subject,
			String body, String url, Response[] responses) {
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.description = body;
		this.url=url;
		this.responses=responses;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonProperty("creator")
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	@JsonIgnore
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	@JsonProperty("subject")
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@JsonIgnore
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	@JsonProperty("timeStamp")
	public String getTimeString(){
		return time.toString();
	}
	
	@JsonIgnore
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	@JsonProperty("responses")
	public Response[] getResponses() {
		return responses;
	}

	public void setResponses(Response[] responses) {
		this.responses = responses;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", sender=" + sender + ", receiver="
				+ receiver + ", subject=" + subject + ", description="
				+ description + ", url=" + url + ", time=" + time
				+ ", checked=" + checked + ", responses="
				+ Arrays.toString(responses) + "]";
	}

}
