package cwrc.notification.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import cwrc.notification.logic.Notification;
import cwrc.notification.logic.Response;
import cwrc.notification.util.Convertor;
import cwrc.notification.util.DbException;

// This class is used for persisting notifications to and retrieving notifications from DB 
public class DAO {
	private static DAO dao;
	private Connection connection;
	
	private PreparedStatement insertNotificationStm;
	private PreparedStatement getLastInsertId;
	private PreparedStatement insertResponseStm;
	private PreparedStatement getNotificationStm;
	private PreparedStatement getResponseStm;
	private PreparedStatement checkStm;
	private ResultSet resultSet;
	private Properties properties;
	
	private DAO() throws DbException  {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			properties=new Properties();
			properties.put("user", "cwrc_workflow");
			properties.put("password", "L2DGN27H1Rm6D8Zv737niw");
		} catch (ClassNotFoundException e) {
			throw new DbException("Could not find com.mysql.jdbc.Driver class. Probably the JDBC lib is missing.");
		}
	}

	public static DAO getInstance() throws DbException {
		if(dao==null){
			dao=new DAO();
		}
		return dao;
	}
	
	// This method makes the connection to DB
	private void setup() throws DbException{
		try {
			if(connection==null || !connection.isValid(0)){
				connection = DriverManager.getConnection("jdbc:mysql://localhost/",properties);
				
				insertNotificationStm = connection.prepareStatement("INSERT INTO cwrc_workflow.notifications(sender, receiver, subject, description, url, time, checked) VALUES (?, ?, ?, ?, ?, null, 0)");
				getLastInsertId = connection.prepareStatement("SELECT LAST_INSERT_ID()");
				insertResponseStm = connection.prepareStatement("INSERT INTO cwrc_workflow.responses(id, value, action) VALUES (?, ?, ?)");
				getNotificationStm = connection.prepareStatement("SELECT id, sender, subject, description, url, time FROM cwrc_workflow.notifications WHERE receiver=? AND checked=0");
				getResponseStm = connection.prepareStatement("SELECT value, action FROM cwrc_workflow.responses WHERE id=?");
				checkStm = connection.prepareStatement("UPDATE cwrc_workflow.notifications SET checked=1 WHERE id=?");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException("DB failed to be instantiated. :"+e.getMessage());
		}
	}
	
	
	// This method inserts a notification into DB
	public void insert(Notification notification) throws DbException{
		setup();
		
		try {
			insertNotificationStm.setString(1, notification.getSender());
			insertNotificationStm.setString(2, notification.getReceiver());
			insertNotificationStm.setString(3, notification.getSubject());
			insertNotificationStm.setString(4, notification.getDescription());
			insertNotificationStm.setString(5, notification.getUrl());
			insertNotificationStm.executeUpdate();
			
			int id=-1;
			ResultSet rs = getLastInsertId.executeQuery();  
			if (rs.next())  
			{  
			 id = rs.getInt("last_insert_id()");              
			} 
			else
				throw new DbException("Failed to get the id of the insertet notificatoin");
			
			for (Response response : notification.getResponses()) {
				insertResponseStm.setInt(1, id);
				insertResponseStm.setString(2, response.getValue());
				insertResponseStm.setString(3, response.getAction());
				insertResponseStm.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException("Cannot insert notifiation={"+notification.toString()+"}. :"+e.getMessage());
		}
	}

	// This method is used to check ('mark as read') the notification indicated with the provided ID 
	public void check(String id) throws DbException {
		setup();
		
		try {
			checkStm.setString(1, id);
			checkStm.executeUpdate();
		} catch (SQLException e) {
			throw new DbException("Cannot check notification with id={"+id+"}. :"+e.getMessage());
		}
	}
	
	// This method returns all the not-checked notifications for the provided receiver
	public ArrayList<Notification> getNotifications(String receiver) throws DbException {
		setup();
		
		try {
			getNotificationStm.setString(1, receiver);
		    ResultSet rs = getNotificationStm.executeQuery();
		    
		    ArrayList<Notification> notifications = new ArrayList<Notification>();
	  
			while(rs.next()){
				Notification notification = new Notification();
				notification.setId(rs.getString(1));
				notification.setSender(rs.getString(2));
				notification.setReceiver(receiver);
				notification.setSubject(rs.getString(3));
				notification.setDescription(rs.getString(4));
				notification.setUrl(rs.getString(5));
				notification.setTime(rs.getTimestamp(6));
				notification.setChecked(false);
				
				getResponseStm.setString(1, notification.getId());
				ResultSet rss = getResponseStm.executeQuery();
				
				ArrayList<Response> responses = new ArrayList<Response>();
				while(rss.next()){
					Response response = new Response();
					response.setValue(rss.getString(1));
					response.setAction(rss.getString(2));
					
					responses.add(response);
				}
				notification.setResponses(Convertor.Arraylist2Array(responses));
				
				notifications.add(notification);
			}
			return notifications;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException("Cannot load notifications. :"+e.getMessage());
		}
	}

	@Override
	protected void finalize(){
		try {
			resultSet.close();
		} catch (SQLException e) {
		}
		
		try {
			insertNotificationStm.close();
		} catch (SQLException e) {
		}
		
		try {
			getNotificationStm.close();
		} catch (SQLException e) {
		}
		
		try {
			checkStm.close();
		} catch (SQLException e) {
		}

		try {
			connection.close();
		} catch (SQLException e) {
		}
	}
}

