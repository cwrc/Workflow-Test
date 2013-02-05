package cwrc.notification.logic;

import java.util.ArrayList;

import cwrc.notification.data.DAO;
import cwrc.notification.util.DbException;

// This is a wrapper for notification dao
public class Manager {
	private DAO dao;
	public Manager() throws DbException {
		dao = DAO.getInstance();
	}
	
	// This method inserts a notification into DB
	public void AddNotification(Notification notification) throws DbException{
		dao.insert(notification);
	}
	
	// This method is used to check ('mark as read') the notification indicated with the provided ID 
	public void CheckNotification(String id) throws DbException{
		dao.check(id);
	}
	
	// This method returns all the not-checked notifications for the provided receiver
	public ArrayList<Notification> GetNotifications(String receiver) throws DbException{
		return dao.getNotifications(receiver);
	}

}
