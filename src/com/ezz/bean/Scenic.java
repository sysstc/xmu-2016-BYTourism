package com.ezz.bean;

import cn.bmob.v3.BmobObject;
import android.R.integer;

public class Scenic extends BmobObject{
	private int id;
	private String scenicname;
	private String describe;
	private int cityid;
	private float avggrade;
	private int gradenum;
	private String scenicview;
	private int scenicprice;
	private  String scenictype;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getScenicname() {
		return scenicname;
	}
	public void setScenicname(String scenicname) {
		this.scenicname = scenicname;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public int getCityid() {
		return cityid;
	}
	public void setCityid(int cityid) {
		this.cityid = cityid;
	}
	public float getAvggrade() {
		return avggrade;
	}
	public void setAvggrade(float avggrade) {
		this.avggrade = avggrade;
	}
	public int getGradenum() {
		return gradenum;
	}
	public void setGradenum(int gradenum) {
		this.gradenum = gradenum;
	}
	public String getScenicview() {
		return scenicview;
	}
	public void setScenicview(String scenicview) {
		this.scenicview = scenicview;
	}
	public int getScenicprice() {
		return scenicprice;
	}
	public void setScenicprice(int scenicprice) {
		this.scenicprice = scenicprice;
	}
	public String getScenictype() {
		return scenictype;
	}
	public void setScenictype(String scenictype) {
		this.scenictype = scenictype;
	}
	
}
