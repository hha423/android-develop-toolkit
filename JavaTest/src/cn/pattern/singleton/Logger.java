package cn.pattern.singleton;

public class Logger {
	private static Logger mLogger = null;
	private final static String TAG = Logger.class.getSimpleName();
	

	private Logger() {
		System.out.println(TAG);
	}
	
	public synchronized static Logger getInstance() {
		if (mLogger == null) {
			mLogger = new Logger();
		}
		return mLogger;
	}

	public void show() {
		System.out.println("print message...");
	}

}


//public class Singleton {
//	protected Singleton() {
//		
//	}
//	
//	public static Singleton getInstance() {
//		return instance;
//	}
//	
//	public String toString() {
//		return "���ǻʵۣ�����һ����";
//	}
//
//	private static Singleton instance = new Singleton();
//}