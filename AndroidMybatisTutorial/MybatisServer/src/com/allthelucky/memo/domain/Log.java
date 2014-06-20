package com.allthelucky.memo.domain;

import java.io.Serializable;

public class Log implements Serializable {
	private static final long serialVersionUID = -5644530969330104266L;
	private int id;
	private int userid;
	private String createtime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}
