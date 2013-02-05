package cwrc.workflows.publishing.states;


import cwrc.notification.NotificationAPI;
import cwrc.workflows.publishing.WorkflowEventHandler;
import cwrc.workflows.publishing.events.EventApprove;

import wfms.engine.Data;
import wfms.engine.State;
import wfms.engine.WfmsException;

public class StatePending extends WorkflowState {

	public static final String name = "Pending";
	public static final boolean isFinal = false;

	class EventHandler extends WorkflowEventHandler {

		@Override
		public State handleEvent(EventApprove event) throws WfmsException {
			
			return new StatePublished(getData());
		}
	}
	
	public StatePending() {
		super(name, isFinal);
		setEventHandler(new EventHandler());
	}

	public StatePending(Data data) throws WfmsException {
		this();
		setData(data);
		
		NotificationAPI.SendNotification("1", "Workflow #"+data.getWfId(), "Drafting");
	}

}
