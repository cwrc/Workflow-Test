package cwrc.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import wfms.engine.State;
import wfms.engine.WfmsException;
import wfms.engine.Workflow;
import wfms.engine.IWorkflows;

// This class is used for persisting workflows in and retrieving workflows from DB
public class CwrcDb implements IWorkflows{
	private static CwrcDb dao;
	private Connection connection;
	
	private PreparedStatement insertStatement;
	private PreparedStatement selectStatement;
	private PreparedStatement updateStatement;
	private ResultSet resultSet;
	private Properties properties;
	
	private CwrcDb() throws WfmsException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			properties=new Properties();
			properties.put("user", "cwrc_workflow");
			properties.put("password", "L2DGN27H1Rm6D8Zv737niw");
			
		} catch (ClassNotFoundException e) {
			throw new WfmsException("Class 'com.mysql.jdbc.Driver' was not fount. : "+e.getMessage());
		}
	}

	public static CwrcDb getInstance() throws WfmsException {
		if(dao==null){
			dao=new CwrcDb();
		}
		return dao;
	}
	
	private void setup() throws WfmsException{
		try {
			if(connection==null || !connection.isValid(0)){
				connection = DriverManager.getConnection("jdbc:mysql://localhost/",properties);
				
				insertStatement = connection.prepareStatement("INSERT INTO cwrc_workflow.instances(wfid, active, type, creator, state, arg) VALUES (?, ?, ?, ?, ?, ?)");
				selectStatement = connection.prepareStatement("SELECT type, creator, state, arg FROM cwrc_workflow.instances WHERE wfid=? AND active");
				updateStatement = connection.prepareStatement("UPDATE cwrc_workflow.instances SET active=?, state=?, arg=? WHERE wfid=?");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WfmsException("Workflow DB failed to be instantiated. :"+e.getMessage());
		}
	}
	
	public void add(Workflow workflow) throws WfmsException{
		setup();
		
		try {
			insertStatement.setString(1, workflow.getWfId());
			insertStatement.setBoolean(2, workflow.isActive());
			insertStatement.setString(3, workflow.getClass().getName());
			insertStatement.setString(4, workflow.getCreator());
			insertStatement.setString(5, workflow.getStateClassName());
			insertStatement.setString(6, workflow.getSerializedData());
			
			insertStatement.executeUpdate();
		} catch (SQLException e) {
			throw new WfmsException("Cannot insert workflow={"+workflow.toString()+"}. :"+e.getMessage());
		}
	}

	public void update(Workflow workflow) throws WfmsException {
		setup();
		
		try {
			updateStatement.setBoolean(1, workflow.isActive());
			updateStatement.setString(2, workflow.getStateClassName());
			updateStatement.setString(3, workflow.getSerializedData());
			updateStatement.setString(4, workflow.getWfId());
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			throw new WfmsException("Cannot update workflow={"+workflow.toString()+"}. :"+e.getMessage());
		}
	}

	public Workflow get(String wfid) throws WfmsException{
		setup();
		
		try {
			selectStatement.setString(1, wfid);
		    ResultSet rs = selectStatement.executeQuery();
		    
		    if (!rs.next())
		    	throw new WfmsException("No active workflow with wfid="+wfid);
		    	
		    
		   String workflowClassName =rs.getString(1);
		   String creator =rs.getString(2);
		   String stateClassName =rs.getString(3);
		   String serializedData =rs.getString(4);
		   
		   State state=(State)Class.forName(stateClassName).newInstance();
		   Workflow workflow = (Workflow)Class.forName(workflowClassName).newInstance();
		   workflow.init(wfid, creator, state, serializedData);
		    
		    return workflow;
		} catch (SQLException e) {
			throw new WfmsException("Cannot load workflow. :"+e.getMessage());
		} catch (InstantiationException e) {
			throw new WfmsException("Cannot load workflow. :"+e.getMessage());
		} catch (IllegalAccessException e) {
			throw new WfmsException("Cannot load workflow. :"+e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new WfmsException("Cannot load workflow. :"+e.getMessage());
		}
	}
	
	public boolean contains(String wfid) throws WfmsException{
		setup();
		
		try {
			selectStatement.setString(1, wfid);
		    ResultSet rs = selectStatement.executeQuery();
		    
		    if (rs.next())
		    	return true;
		    else
		    	return false;
		    	
		} catch (SQLException e) {
			throw new WfmsException("Cannot load workflow. :"+e.getMessage());
		}
	}
	
	@Override
	protected void finalize(){
		try {
			resultSet.close();
		} catch (SQLException e) {
		}
		
		try {
			insertStatement.close();
		} catch (SQLException e) {
		}
		
		try {
			selectStatement.close();
		} catch (SQLException e) {
		}
		
		try {
			updateStatement.close();
		} catch (SQLException e) {
		}

		try {
			connection.close();
		} catch (SQLException e) {
		}
	}
}

