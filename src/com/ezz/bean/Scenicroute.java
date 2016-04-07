package com.ezz.bean;

import cn.bmob.v3.BmobObject;



public class Scenicroute extends BmobObject{
	private int id;
	private int one;
	private int two;
	private int three;
	private int four;
	private int five;
	public int getFive() {
		return five;
	}
	public void setFive(int five) {
		this.five = five;
	}
	private int cityid;
	private String describe;
	private  int avggrade;
	private int gradenum;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOne() {
		return one;
	}
	public void setOne(int one) {
		this.one = one;
	}
	public int getTwo() {
		return two;
	}
	public void setTwo(int two) {
		this.two = two;
	}
	public int getThree() {
		return three;
	}
	public void setThree(int three) {
		this.three = three;
	}
	public int getFour() {
		return four;
	}
	public void setFour(int four) {
		this.four = four;
	}
	public int getCityid() {
		return cityid;
	}
	public void setCityid(int cityid) {
		this.cityid = cityid;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public int getAvggrade() {
		return avggrade;
	}
	public void setAvggrade(int avggrade) {
		this.avggrade = avggrade;
	}
	public int getGradenum() {
		return gradenum;
	}
	public void setGradenum(int gradenum) {
		this.gradenum = gradenum;
	}
	
}
