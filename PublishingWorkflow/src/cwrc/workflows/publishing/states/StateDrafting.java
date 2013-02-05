package cwrc.workflows.publishing.states;

import cwrc.notification.NotificationAPI;
import cwrc.workflows.publishing.WorkflowEventHandler;
import cwrc.workflows.publishing.events.EventSubmit;

import wfms.engine.Data;
import wfms.engine.State;
import wfms.engine.WfmsException;

public class StateDrafting extends WorkflowState {

	public static final String name = "Drafting";
	public static final boolean isFinal = false;

	class EventHandler extends WorkflowEventHandler {

		@Override
		public State handleEvent(EventSubmit event) throws WfmsException {
			
			return new StateSChecking(getData());
		}
	}
	
	public StateDrafting() {
		super(name, isFinal);
		setEventHandler(new EventHandler());
	}

	public StateDrafting(Data data) throws WfmsException {
		this();
		setData(data);
		
		NotificationAPI.SendNotification("1", "Workflow #"+data.getWfId(), "Drafting");
	}

}
