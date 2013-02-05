package cwrc.workflows.publishing;

import wfms.engine.Data;

// This class specifies the workflow-specific data of workflows.
public class WorkflowData extends Data {
	
	private final static String wfName="HQP User Account Creation";
	private String id;
	
	public WorkflowData() {
		super(wfName);
	}

	public WorkflowData(String wfId, String wfCreator, String id) {
		super(wfName, wfId, wfCreator);
		this.id=id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String serialize() {
		
		return id;
	}

	@Override
	public void deserialize(String data) {
		id=data;
	}
}
