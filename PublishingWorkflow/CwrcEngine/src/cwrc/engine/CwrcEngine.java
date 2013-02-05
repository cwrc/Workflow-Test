package cwrc.engine;

import wfms.engine.Engine;
import wfms.engine.TimeManager;
import wfms.engine.WfmsException;

public class CwrcEngine  extends Engine {
	private static CwrcEngine cwrc;
	
	private CwrcEngine() throws WfmsException{
		// super(new SimpleCollection(), 0);
		super(CwrcDb.getInstance(), 0);
 	}
	
	public static CwrcEngine getInstance() throws WfmsException{
		if(cwrc==null){
			cwrc=new CwrcEngine();
			TimeManager.init(cwrc);
		}
		return cwrc;
	}
	
}
