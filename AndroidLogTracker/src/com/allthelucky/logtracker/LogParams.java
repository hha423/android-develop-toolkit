package com.allthelucky.logtracker;
public class LogParams {
	
	private int level = LogTracker.LOG_LEVEL_NONE;
	
	private String tag = LogTracker.DEFAULT_TAG;

	public LogParams() {
	}

	public int getLevel() {
		return level;
	}

	public String getTag() {
		return tag;
	}

	public LogParams setLevel(int level) {
		this.level = level;
		return this;
	}

	public LogParams setTag(String tag) {
		this.tag = tag;
		return this;
	}

}