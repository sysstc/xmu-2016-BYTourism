package com.ezz.bean;

import java.sql.Date;

import cn.bmob.v3.BmobObject;


public class Message extends BmobObject{
	private Integer id;
	private int userid;
	private String content;
	private Date time;
	private int scenicid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getScenicid() {
		return scenicid;
	}
	public void setScenicid(int scenicid) {
		this.scenicid = scenicid;
	}
	
}
