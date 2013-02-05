package cwrc.workflows.publishing;

import cwrc.workflows.publishing.events.EventApprove;
import cwrc.workflows.publishing.events.EventInitiate;
import cwrc.workflows.publishing.events.EventMChecked;
import cwrc.workflows.publishing.events.EventReturn;
import cwrc.workflows.publishing.events.EventReviewed;
import cwrc.workflows.publishing.events.EventSChecked;
import cwrc.workflows.publishing.events.EventSubmit;
import wfms.engine.EventHandler;
import wfms.engine.State;
import wfms.engine.WfmsException;

public class WorkflowEventHandler extends EventHandler{
	
	public State handleEvent(EventInitiate event) throws WfmsException{
		throw new WfmsException("Unexpected Event:"+event.getName());
	}

	public State handleEvent(EventApprove event) throws WfmsException{
		throw new WfmsException("Unexpected Event:"+event.getName());
	}

	public State handleEvent(EventMChecked event) throws WfmsException{
		throw new WfmsException("Unexpected Event:"+event.getName());
	}
	
	public State handleEvent(EventSChecked event) throws WfmsException{
		throw new WfmsException("Unexpected Event:"+event.getName());
	}
	
	public State handleEvent(EventReturn event) throws WfmsException{
		throw new WfmsException("Unexpected Event:"+event.getName());
	}
	
	public State handleEvent(EventReviewed event) throws WfmsException{
		throw new WfmsException("Unexpected Event:"+event.getName());
	}
	
	public State handleEvent(EventSubmit event) throws WfmsException{
		throw new WfmsException("Unexpected Event:"+event.getName());
	}

}
