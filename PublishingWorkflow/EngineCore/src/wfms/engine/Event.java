package wfms.engine;

// This class provide the abstraction for events to which the workflow engine should react. For each type of event, a sub-class of this class should be written
public abstract class Event {
	
	private String name;
	private String sender;
	
	public Event(String name, String sender) {
		this.name=name;
		this.sender=sender;
	}
	
	public String getName(){
		return name;
	}

	public String getSender() {
		return sender;
	}
	
	// This method is a part of Visitor pattern used for handling events in workflows.
	public abstract State visit(EventHandler eventHandler) throws WfmsException;
	
}
