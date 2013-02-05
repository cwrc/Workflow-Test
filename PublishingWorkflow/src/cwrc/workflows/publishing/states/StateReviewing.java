package cwrc.workflows.publishing.states;


import cwrc.notification.NotificationAPI;
import cwrc.workflows.publishing.WorkflowEventHandler;
import cwrc.workflows.publishing.events.EventReviewed;

import wfms.engine.Data;
import wfms.engine.State;
import wfms.engine.WfmsException;

public class StateReviewing extends WorkflowState {

	public static final String name = "Reviewing";
	public static final boolean isFinal = false;

	class EventHandler extends WorkflowEventHandler {

		@Override
		public State handleEvent(EventReviewed event) throws WfmsException {
			
			return new StateMChecking(getData());
		}
	}
	
	public StateReviewing() {
		super(name, isFinal);
		setEventHandler(new EventHandler());
	}

	public StateReviewing(Data data) throws WfmsException {
		this();
		setData(data);
		
		NotificationAPI.SendNotification("1", "Workflow #"+data.getWfId(), "Drafting");
	}

}
