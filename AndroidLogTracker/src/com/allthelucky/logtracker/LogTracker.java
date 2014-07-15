package com.allthelucky.logtracker;

public interface LogTracker {
	
	public static final int PROGRAME_TYPE = 0;
	
	public static final int BUSINUSS_TYPE = 1;
	
	public static final String DEFAULT_TAG = "*\\_/*";

	public static final int LOG_LEVEL_NONE = -1;

	public static final int LOG_LEVEL_DEBUG = 0;

	public static final int LOG_LEVEL_ERROR = 1;

	public static final int LOG_LEVEL_WARMING = 2;

	public static final int LOG_LEVEL_VERBOSE = 3;
	
	public LogParams getParams();

	public void track(int type, String programExcepMsg);
	
	public void restore();
	
	public void save();
	
}
