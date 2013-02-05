package wfms.engine;

// This class provides the abstraction for the data part of workflows. For any specific workflow, this class should be extended to include workflow-specific data
public abstract class Data {
	
	private String wfName;
	private String wfId;
	private String wfCreator;
	
	public Data(String wfName) {
		this.wfName=wfName;
	}
	
	public Data(String wfName, String wfId,  String wfCreator) {
		this.wfName=wfName;
		this.wfId=wfId;
		this.wfCreator=wfCreator;
	}
	
	public void init(String wfId,  String wfCreator){
		this.wfId=wfId;
		this.wfCreator=wfCreator;
	}

	public String getWfName() {
		return wfName;
	}

	public String getWfId() {
		return wfId;
	}

	public String getWfCreator() {
		return wfCreator;
	}
	
	// This method should be implemented for each specific workflow to serialize the workflow data in order to store it in DB
	public abstract String serialize();
	
	// This method should be implemented for each specific workflow to deserialize retrived data from DB into workflow data
	public abstract void deserialize(String data);
}
