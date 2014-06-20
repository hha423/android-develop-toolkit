package com.allthelucky.memo.domain;

import java.io.Serializable;

public class Sms implements Serializable {
	private static final long serialVersionUID = 6528004031308927629L;
	private int id;
	private String mobile;
	private String code;
	private String createtime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}
