package cwrc.workflows.publishing.states;


import cwrc.notification.NotificationAPI;
import cwrc.workflows.publishing.WorkflowEventHandler;

import wfms.engine.Data;
import wfms.engine.WfmsException;

public class StatePublished extends WorkflowState {

	public static final String name = "Published";
	public static final boolean isFinal = true;

	class EventHandler extends WorkflowEventHandler {

	}
	
	public StatePublished() {
		super(name, isFinal);
		setEventHandler(new EventHandler());
	}

	public StatePublished(Data data) throws WfmsException {
		this();
		setData(data);
		
		NotificationAPI.SendNotification("1", "Workflow #"+data.getWfId(), "Drafting");
	}

}
