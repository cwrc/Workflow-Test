package wfms.engine;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

// This class is used for defining (and executing) time events
public class TimeManager {
	private Map<String, Timer> events;
	private Engine engine;
	private static TimeManager manager;
	
	private TimeManager(Engine engine){
		events=new HashMap<String, Timer>();
		this.engine=engine;
	}
	
	public static void init(Engine engine){
		manager=new TimeManager(engine);
	}
	
	public static TimeManager getInstance(){
		return manager;
	}
	
	// This method sets that a time-event on the provided date is triggered on the workflow indicating with the provided ID 
	public void addEvent(String wfid,Date time){
		if(!events.containsKey(wfid))
			events.put(wfid, new Timer());
		
		events.get(wfid).schedule(new TimeEventTask( wfid, new TimeEvent()), time);
	}
	
	// This method sets that a time-event in the specified days is triggered on the workflow indicating with the provided ID
	public void addEvent(String wfid,int day){
		if(!events.containsKey(wfid))
			events.put(wfid, new Timer());
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH,day);
		
		events.get(wfid).schedule(new TimeEventTask( wfid, new TimeEvent()), cal.getTime());
	}
	
	public void cancelEvent(String wfid){
		Timer timer =events.remove(wfid);
		timer.cancel();
	}
	
	class TimeEventTask extends TimerTask{
		
		private String id;
		private TimeEvent event;
		
		public TimeEventTask( String id, TimeEvent event) {
			this.id=id;
			this.event=event;
		}

		@Override
		public void run() {
			try {
				engine.trigger(id, event);
			} catch (WfmsException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class TimeEvent extends Event{

		private static final String name="Time";
		public TimeEvent() {
			super(name, "0");
		}

		@Override
		public State visit(EventHandler eventHandler) throws WfmsException {
			return eventHandler.handleEvent(this);
		}
	}
}
