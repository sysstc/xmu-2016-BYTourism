package com.ezz.bean;

import cn.bmob.v3.BmobObject;


public class Activitymen extends BmobObject{
	private Integer userid ;
	private Integer id;
	private int activityid;
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getActivityid() {
		return activityid;
	}
	public void setActivityid(int activityid) {
		this.activityid = activityid;
	}
	
}
