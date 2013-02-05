package cwrc.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import cwrc.engine.CwrcEngine;
import cwrc.workflows.publishing.events.EventApprove;
import cwrc.workflows.publishing.events.EventMChecked;
import cwrc.workflows.publishing.events.EventReturn;
import cwrc.workflows.publishing.events.EventReviewed;
import cwrc.workflows.publishing.events.EventSChecked;
import cwrc.workflows.publishing.events.EventSubmit;

import wfms.engine.Engine;
import wfms.engine.WfmsException;

// This is the service class for the sample publishing workflow. This class mainly declare methods which transform incoming calls into events and trigger them on the corresponding workflow instance. For every workflow, one workflow service class (like this one) should be written.
public class PublishingWorkflowService {
	
	private String wfid;
	private Engine engine;
	
	public PublishingWorkflowService(String wfid) throws Exception {
		this.wfid=wfid;
		engine=CwrcEngine.getInstance();
	}
	
	@GET
	@Path("state")
	@Produces(MediaType.TEXT_PLAIN)
	public String getState() throws Exception {
		return engine.getStateName(wfid);
	}
	
	@GET
	@Path("creator")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCreator() throws Exception {
		return engine.getCreator(wfid);
	}
	
	@POST
	@Path("Initiate")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Object initiateEvent( @FormParam("sender") String sender, @FormParam("id") String id) throws Exception {
		if(sender==null || sender.isEmpty())
			throw new WfmsException("Sender is required");
		if(engine.hasWorkflow(wfid))
			throw new WfmsException("There is another active workflow instance with the wfid="+wfid);
		engine.addWorkflow( new cwrc.workflows.publishing.Workflow(wfid,sender,id.isEmpty()?null:id));
		return Response.status(Status.OK).build();
	}
	
	@POST
	@Path("Approve")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Object approveEvent( @FormParam("sender") String sender) throws Exception {
		if(sender==null || sender.isEmpty())
			throw new WfmsException("Sender is required");
		engine.trigger(wfid, new EventApprove( sender));
		return Response.status(Status.OK).build();
	}
	
	@POST
	@Path("MChecked")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Object mcheckedEvent(@FormParam("sender") String sender) throws Exception {
		if(sender==null || sender.isEmpty())
			throw new WfmsException("Sender is required");
		engine.trigger(wfid, new EventMChecked( sender));
		return Response.status(Status.OK).build();
	}
	
	@POST
	@Path("Reviewed")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Object reviewedEvent(@FormParam("sender") String sender) throws Exception {
		if(sender==null || sender.isEmpty())
			throw new WfmsException("Sender is required");
		engine.trigger(wfid, new EventReviewed( sender));
		return Response.status(Status.OK).build();
	}
	
	@POST
	@Path("SChecked")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Object scheckedEvent(@FormParam("sender") String sender) throws Exception {
		if(sender==null || sender.isEmpty())
			throw new WfmsException("Sender is required");
		engine.trigger(wfid, new EventSChecked( sender));
		return Response.status(Status.OK).build();
	}
	
	@POST
	@Path("Submit")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Object submitEvent(@FormParam("sender") String sender) throws Exception {
		if(sender==null || sender.isEmpty())
			throw new WfmsException("Sender is required");
		engine.trigger(wfid, new EventSubmit( sender));
		return Response.status(Status.OK).build();
	}
	
	@POST
	@Path("Return")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Object triggerApproveEvent(@FormParam("sender") String sender) throws Exception {
		if(sender==null || sender.isEmpty())
			throw new WfmsException("Sender is required");
		engine.trigger(wfid, new EventReturn( sender));
		return Response.status(Status.OK).build();
	}
	
	
}

