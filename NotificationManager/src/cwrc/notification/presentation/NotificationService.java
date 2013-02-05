package cwrc.notification.presentation;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.map.ObjectMapper;

import cwrc.notification.logic.Manager;
import cwrc.notification.logic.Notification;
import cwrc.notification.logic.Response;
import cwrc.notification.util.DbException;

// This class implements a REST api for the notification manager by which external components can access or manipulate notifications through HTTP method calls
@Path("/notifications")
public class NotificationService {
	
	private Manager manager;
	private ObjectMapper mapper;
	public NotificationService() throws DbException {
		manager= new Manager();
		mapper= new ObjectMapper();
	}
	
	@Context
	UriInfo uriInfo;
	
	@GET
	@Path("status")
	@Produces(MediaType.TEXT_PLAIN)
	public String getState() throws Exception {
		return "Working!";
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void add(@FormParam("sender") String sender, @FormParam("receiver") String receiver, @FormParam("subject") String subject, @FormParam("description") String description, @FormParam("url") String url, @FormParam("responses") String responses) throws Exception{
		if(sender==null || sender.isEmpty())
			throw new Exception("sender is required.");
		if(receiver==null || receiver.isEmpty())
			throw new Exception("receiver is required.");
		if(subject==null || subject.isEmpty())
			throw new Exception("subject is required.");
		if(description==null || description.isEmpty())
			throw new Exception("description is required.");
		
		Notification notification = new Notification();
		notification.setSender(sender);
		notification.setReceiver(receiver);
		notification.setSubject(subject);
		notification.setDescription(description);
		notification.setUrl((url==null || url.isEmpty())?null:url);
		
		if(responses!=null && !responses.isEmpty()){
			notification.setResponses(mapper.readValue(responses, Response[].class));
		}
		
		manager.AddNotification(notification);
	}
	
	@POST
	@Path("check/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void check(@PathParam("id") String id) throws Exception{
		if(id==null || id.isEmpty())
			throw new Exception("id is required.");
		
		manager.CheckNotification(id);

	}
	
	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Object get(@PathParam("id") String id) throws Exception{
		ArrayList<Notification> notifications = manager.GetNotifications(id);
		for (int i = 0; i < notifications.size(); i++) {
			if(notifications.get(i).getResponses().length == 0){
				Response defaultResponse= new Response("Mark", "http://apps.testing.cwrc.ca:8080/NotificationManager/notifications/check/"+notifications.get(i).getId());
				notifications.get(i).setResponses(new Response[] {defaultResponse});
			}
		}
		return mapper.writeValueAsString(notifications);
	}
}
