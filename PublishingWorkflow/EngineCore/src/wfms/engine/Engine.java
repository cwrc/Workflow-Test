package wfms.engine;

import java.util.LinkedHashMap;
import java.util.Map;

// This class implements the core of the workflow engine.
public class Engine {
	
	private IWorkflows workflows;
	private Cache cache;
	
	// The class should be initialized with an IWorkflows instance (which is in fact an interface into a collection of workflow such as workflow DB) and a cache size
	public Engine(IWorkflows workflows, int cacheSize){
		this.workflows=workflows;
		if(cacheSize!=0)
			cache=new Cache(cacheSize);
	}
	
	
	// This method return the workflow with the provided id
	private Workflow getWorkflow(String wfid) throws  WfmsException {
		if(cache!=null && cache.containsKey(wfid))
			return cache.get(wfid);
		
		Workflow workflow = workflows.get(wfid);
		if(cache!=null)
			cache.put(wfid, workflow);
		return workflow;
	}
		
	// This method checks whether there is a workflow with the provided id or not
	public boolean hasWorkflow(String wfid) throws WfmsException{
		if(cache!=null && cache.containsKey(wfid))
			return true;
		
		return workflows.contains(wfid);
	}
	
	// This method returns the name of the state of the workflow with the provided id
	public String getStateName(String wfid) throws WfmsException{
		return getWorkflow(wfid).getStateName();
	}
	
	
	// This method returns the name of the creator of the workflow with the provided id
	public String getCreator(String wfid) throws WfmsException{
		return getWorkflow(wfid).getCreator();
	}
	
	// This method adds the provided workflow to the workflow collection
	public void addWorkflow(Workflow workflow) throws WfmsException{
		if(hasWorkflow(workflow.getWfId()))
			throw new WfmsException("The workflow with WFID="+workflow.getWfId()+" cannot be added. There is another active workflow with the same WFID");
		
		workflows.add(workflow);
		if(cache!=null && workflow.isActive())
			cache.put(workflow.getWfId(), workflow);
	}
	
	
	// This method is used to send an event to a particular workflow
	public void trigger(String wfid, Event event) throws WfmsException{
		Workflow workflow = getWorkflow(wfid);
		workflow.handleEvent(event);
		workflows.update(workflow);
		
		if(!workflow.isActive() && cache!=null && cache.containsKey(workflow.getWfId())){
			cache.remove(workflow);
		}
	}
	
	// This class implements a cache for the workflow engine
	class Cache extends LinkedHashMap<String, Workflow>{

		private static final long serialVersionUID = 8010477951843522451L;
		
		private int MAX_ENTRIES;
		
		public Cache(int MAX_ENTRIES) {
			super(MAX_ENTRIES,1.0f,true);
			this.MAX_ENTRIES = MAX_ENTRIES;
		}

		@Override
		protected boolean removeEldestEntry(Map.Entry<String,Workflow> eldest) {
			return size() > MAX_ENTRIES;
		}
	}
}
