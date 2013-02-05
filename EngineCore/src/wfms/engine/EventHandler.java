package wfms.engine;

import wfms.engine.TimeManager.TimeEvent;

// This class provides the abstraction (in fact, more of a place holder) for event-handlers. Every state should have a sub-class of this class which encompasses the logic of how each event should be handeled. 
public abstract class EventHandler{
		
	public State handleEvent(Event event) throws WfmsException{
		throw new WfmsException("Irrelevant Event:"+event.getName());
	}
	
	public State handleEvent(TimeEvent event) throws WfmsException{
		throw new WfmsException("Irrelevant Event:"+event.getName());
	}
}
