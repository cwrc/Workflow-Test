package cwrc.engine;

import java.util.HashMap;
import java.util.Map;

import wfms.engine.IWorkflows;
import wfms.engine.WfmsException;
import wfms.engine.Workflow;

public class SimpleCollection implements IWorkflows {
	private Map<String, Workflow> workflows;
	
	public SimpleCollection() {
		workflows=new HashMap<String, Workflow>();
	}

	@Override
	public Workflow get(String id) throws WfmsException {
		if(!workflows.containsKey(id))
			throw new WfmsException("No active active workflow with the wfid="+id+" found.");

		return workflows.get(id);
	}

	@Override
	public void update(Workflow workflow) throws WfmsException {
		if(!workflow.isActive())
			workflows.remove(workflow);
	}

	@Override
	public boolean contains(String id) throws WfmsException {
		if(workflows.containsKey(id))
			return true;
		else 
			return false;
	}

	@Override
	public void add(Workflow workflow) throws WfmsException {
		if(workflow.isActive())
			workflows.put(workflow.getWfId(), workflow);
	}

	public int size() throws WfmsException {
		return workflows.size();
	}
}
