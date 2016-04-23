package com.ezz.bean;

import android.R.integer;
import cn.bmob.v3.BmobObject;

public class Friend extends BmobObject{
	private Integer id;
	private int oneid;
	private int twoid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOneid() {
		return oneid;
	}
	public void setOneid(int oneid) {
		this.oneid = oneid;
	}
	public int getTwoid() {
		return twoid;
	}
	public void setTwoid(int twoid) {
		this.twoid = twoid;
	}
	
}
