package wfms.engine;

// This class provide the abstraction for states. Any workflow should have one sub-class of this class for each of its states.
public abstract class State {

	private String name;
	private boolean isFinal; // This field specifies whether the state is a final state or not
	private EventHandler eventHandler; // This field contains the event handler for handling the events sent to the state
	private Data data;
	
	public State(String name, boolean isFinal) {
		this.name = name;
		this.isFinal=isFinal;
	}

	// This method is a part of Visitor pattern used for handeling events.
	public State handleEvent(Event event) throws WfmsException {
		return event.visit(getEventHandler());
	}

	public String getName() {
		return name;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public EventHandler getEventHandler() {
		return eventHandler;
	}

	public void setEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data=data;
	}
}
