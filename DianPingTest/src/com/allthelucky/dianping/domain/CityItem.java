package com.allthelucky.dianping.domain;

import java.io.Serializable;

public class CityItem implements Serializable {

	private static final long serialVersionUID = -1052882723341202893L;
	public String name;

	public CityItem(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return "CityItem [name=" + name + "]";
	}

}
