package com.allthelucky.examples.common;

public class DeviceInfoUtils {

	public String getDeviceInfoField() {
		String latitude = "114.218927345210";
		String longtitude = "29.5754297789240";
		String ip = "FFFFFFFF";
		String accuracy = "FFFFFFFF";
		StringBuilder sb = new StringBuilder();
		
		sb.append(latitude);
		sb.append(longtitude);
		sb.append(ip);
		sb.append(accuracy);
		return sb.toString(); 
	}

}
