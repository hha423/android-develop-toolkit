package com.allthelucky.memo.domain;

import java.io.Serializable;

public class Sugest implements Serializable {
	private static final long serialVersionUID = 492205229998206993L;
	private int id;
	private int userid;
	private String content;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}
