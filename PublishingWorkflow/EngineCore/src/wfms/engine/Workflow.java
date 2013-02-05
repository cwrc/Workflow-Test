package wfms.engine;

// This class provide the abstraction for workflows. A sub-class of this class should be written for each workflow wants to be supported in the system.
public abstract class Workflow{
	
	private Data data; // The field contains the data part of workflow
	private State state;
	
	public Workflow(Data data) {
		this.data=data;
	}
	
	public Data getData() {
		return data;
	}
	
	public Data setData() {
		return data;
	}
	
	public String getWfId(){
		return data.getWfId();
	}

	public String getName() {
		return data.getWfName();
	}
	
	public String getCreator(){
		return data.getWfCreator();
	}
	
	public String getSerializedData() {
		return data.serialize();
	}
	
	public State getState(){
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}

	public String getStateName() {
		return state.getName();
	}
	
	public String getStateClassName(){
		return state.getClass().getName();
	}
	
	public boolean isActive(){
		return !state.isFinal();
	}
	
	public void handleEvent(Event event) throws WfmsException {
		state = state.handleEvent(event);
	}

	public void init(String wfid, String creator, State state, String arg) throws WfmsException{
		data.init(wfid, creator);
		data.deserialize(arg);
		
		this.state=state;
		state.setData(data);
	}
	
	@Override
	public String toString() {
		return "WfId="+getWfId()+" & type="+getClass().getName()+" & creator="+getCreator()+" & state="+getStateClassName()+" & arg="+getSerializedData();
	}
}
