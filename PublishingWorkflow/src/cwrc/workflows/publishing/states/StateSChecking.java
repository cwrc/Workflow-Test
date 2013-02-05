package cwrc.workflows.publishing.states;


import cwrc.notification.NotificationAPI;
import cwrc.workflows.publishing.WorkflowEventHandler;
import cwrc.workflows.publishing.events.EventSChecked;

import wfms.engine.Data;
import wfms.engine.State;
import wfms.engine.WfmsException;

public class StateSChecking extends WorkflowState {

	public static final String name = "Source Checking";
	public static final boolean isFinal = false;

	class EventHandler extends WorkflowEventHandler {

		@Override
		public State handleEvent(EventSChecked event) throws WfmsException {
			
			return new StateReviewing(getData());
		}
	}
	
	public StateSChecking() {
		super(name, isFinal);
		setEventHandler(new EventHandler());
	}

	public StateSChecking(Data data) throws WfmsException {
		this();
		setData(data);
		
		NotificationAPI.SendNotification("1", "Workflow #"+data.getWfId(), "Drafting");
	}

}
