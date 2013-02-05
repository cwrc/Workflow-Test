package cwrc.workflows.publishing.states;

import cwrc.workflows.publishing.WorkflowData;
import wfms.engine.State;

public abstract class WorkflowState extends State{
	
	public WorkflowState(String name, boolean isFinal) {
		super(name, isFinal);
	}

	@Override
	public WorkflowData getData() {
		return (WorkflowData) super.getData();
	}

}
