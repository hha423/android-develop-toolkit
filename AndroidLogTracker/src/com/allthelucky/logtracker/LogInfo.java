package com.allthelucky.logtracker;

import org.json.JSONException;
import org.json.JSONObject;

class LogInfo {
	public int type = 0;
	public String datatime;
	public String programExcepMsg = "";

	public LogInfo() {
	}

	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("type", type);
			obj.put("datatime", datatime);
			obj.put("programExcepMsg", programExcepMsg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
}
