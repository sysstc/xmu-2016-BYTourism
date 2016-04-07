package com.ezz.bean;

import android.R.integer;
import cn.bmob.v3.BmobObject;

public class Collect extends BmobObject{
    private Integer id;
    private int cid;
	private Integer userid;
	private int type;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
