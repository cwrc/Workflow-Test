package cwrc.workflows.publishing.events;

import cwrc.workflows.publishing.WorkflowEventHandler;
import wfms.engine.Event;
import wfms.engine.EventHandler;
import wfms.engine.State;
import wfms.engine.WfmsException;

public class EventApprove extends Event {

	private static final String name = "Approve";
	
	public EventApprove(String sender) {
		super(name, sender);
	}

	@Override
	public State visit(EventHandler eventHandler) throws WfmsException {
		return ((WorkflowEventHandler) eventHandler).handleEvent(this);
	}
}
