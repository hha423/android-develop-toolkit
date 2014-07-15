package com.allthelucky.logtracker;

public class LogFactory {

	public static LogTracker getLogger() {
		return LogTrackManager.getInstance();
	}
	
}
