package com.allthelucky.memo.model;

public class MemoInfo {
	private int id;
	private int userid;
	private String title;
	private String content;
	private String tag;
	private String createt;
	private String updatet;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getCreatet() {
		return createt;
	}

	public void setCreatet(String createt) {
		this.createt = createt;
	}

	public String getUpdatet() {
		return updatet;
	}

	public void setUpdatet(String updatet) {
		this.updatet = updatet;
	}
}
