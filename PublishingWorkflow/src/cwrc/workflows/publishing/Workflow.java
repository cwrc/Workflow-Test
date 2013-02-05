package cwrc.workflows.publishing;

import cwrc.workflows.publishing.states.StateDrafting;

import wfms.engine.WfmsException;

// This class is the main entry-point for workflows.
public class Workflow extends wfms.engine.Workflow {
	
	
	public Workflow() {
		super(new WorkflowData());
	}

	public Workflow(String wfId, String wfCreator, String id) throws WfmsException {
		super(new WorkflowData(wfId,wfCreator,id));
		
		setState(new StateDrafting(getData()));
	}

}
