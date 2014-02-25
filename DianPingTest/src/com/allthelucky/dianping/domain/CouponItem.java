package com.allthelucky.dianping.domain;

import java.io.Serializable;
import java.util.List;

public class CouponItem implements Serializable {

	private static final long serialVersionUID = -3694275887401849886L;
	public String couponId;
	public String title;
	public String description;
	public List<String> regions;
	public List<String> categories;
	
	public String downloadCount;
	public String publishDate;
	public String expirationDate;
	public String distance;
	public String couponUrl;
	public List<String> businesses;//{name;id;url}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
