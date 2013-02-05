package cwrc.notification;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import wfms.engine.WfmsException;
import wfms.utility.restservice.Parameter;
import wfms.utility.restservice.Response;
import wfms.utility.restservice.RestClient;

// This class provides a REST client for Notification Manager component.
public class NotificationAPI {
	
	// The baseURI should point to the base address of notification manager component
	private static String baseURI = "http://localhost:8080/NotificationManager/notifications";
	
	public static void SendNotification(String receiver, String subject, String description) throws WfmsException{
		Set<Parameter> params= new HashSet<Parameter>();
		params.add(new Parameter("sender", "Workflow System"));
		params.add(new Parameter("receiver", receiver));
		params.add(new Parameter("subject", subject));
		params.add(new Parameter("description",description));
		Response r = RestClient.POST(baseURI, params);
		if(r.getStatus()!=200)
			throw new WfmsException("Sending the notification failed.");
	}
	
	public static void SendNotification(Collection<String> receivers, String title, String message) throws WfmsException{
		for(String receiver:receivers){
			SendNotification(receiver, title, message);
		}
	}
	
	public static void SendNotification(String[] receivers, String title, String message) throws WfmsException{
		for(String receiver:receivers){
			SendNotification(receiver, title, message);
		}
	}
}
