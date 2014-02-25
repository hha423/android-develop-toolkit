package com.allthelucky.dianping.domain;

import java.io.Serializable;
import java.util.List;

public class RegionItem implements Serializable {
	private static final long serialVersionUID = -4975441715599538457L;

	public String discrictName;
	public List<String> neighborhoods;

	public RegionItem(String discrictName, List<String> neighborhoods) {
		super();
		this.discrictName = discrictName;
		this.neighborhoods = neighborhoods;
	}

	@Override
	public String toString() {
		return "RegionItem [discrictName=" + discrictName + ", neighborhoods=" + neighborhoods + "]";
	}

}
