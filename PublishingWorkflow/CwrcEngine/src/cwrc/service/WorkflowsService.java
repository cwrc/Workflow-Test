package cwrc.service;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

// This is the main service class which specifies the REST api of the workflow engine. This class acts as the entry-point for REST api calls to workflow engine (it redirects  the calls toward corresponding workflow instances). For every workflow, a method should be added in this class.
@Path("/workflows")
public class WorkflowsService {
	
	@Context
	UriInfo uriInfo;
	
	@GET
	@Path("status")
	@Produces(MediaType.TEXT_PLAIN)
	public String getState() throws Exception {
		return "Working";
	}
	
	@Path("Publishing/{wfid}")
	public PublishingWorkflowService publishing(@PathParam("wfid") String wfid) throws  Exception {
		return new PublishingWorkflowService(wfid);
	}

}
