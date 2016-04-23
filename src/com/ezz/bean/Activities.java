package com.ezz.bean;


import java.util.Date;

import android.R.integer;
import cn.bmob.v3.BmobObject;

public class Activities extends BmobObject{
	private Integer id;
	private String title;
	private String describe;
	private String address;
	private  String type;
	private String ptime;
	private int pstate;
	private int scenicid;
	private int pcount;
	private int pneed_count;
	
	public int getPneed_count() {
		return pneed_count;
	}
	public void setPneed_count(int pneed_count) {
		this.pneed_count = pneed_count;
	}
	public int getPcount() {
		return pcount;
	}
	public void setPcount(int pcount) {
		this.pcount = pcount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPtime() {
		return ptime;
	}
	public void setPtime(String ptime) {
		this.ptime = ptime;
	}
	public int getPstate() {
		return pstate;
	}
	public void setPstate(int pstate) {
		this.pstate = pstate;
	}
	public int getScenicid() {
		return scenicid;
	}
	public void setScenicid(int scenicid) {
		this.scenicid = scenicid;
	}
	
}
