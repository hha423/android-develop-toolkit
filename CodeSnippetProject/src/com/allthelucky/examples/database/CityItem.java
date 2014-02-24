package com.allthelucky.examples.database;

/**
 * 城市信息类
 */
public class CityItem {
	public String latitude;
	public String longitude;
	public String id;
	public String name;
	public String enName;
	public String prefixLetter;

	@Override
	public String toString() {
		return "CityItem [latitude=" + latitude + ", longitude=" + longitude + ", id=" + id + ", name=" + name
				+ ", enName=" + enName + ", prefixLetter=" + prefixLetter + "]";
	}

}
