package com.allthelucky.memo.domain;

import java.io.Serializable;

public class Reply implements Serializable {
	private static final long serialVersionUID = -7358787228471516805L;
	private int id;
	private int userid;
	private int sugestid;
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

	public int getSugestid() {
		return sugestid;
	}

	public void setSugestid(int sugestid) {
		this.sugestid = sugestid;
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
