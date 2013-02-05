package wfms.engine;

// This interface specifies the required interface of any workflow collection  which wants to be used in workflow engine.
public interface IWorkflows {
	Workflow get(String id) throws WfmsException;
	void update(Workflow workflow) throws WfmsException;
	boolean contains(String id) throws WfmsException;
	void add(Workflow workflow) throws WfmsException;
}
