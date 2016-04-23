package com.ezz.view;

import android.R.integer;

public class LeftMenu {
	private int imageView;
	private String username_text;
	public int getImageView() {
		return imageView;
	}
	public void setImageView(int imageView) {
		this.imageView = imageView;
	}
	public String getUsername_text() {
		return username_text;
	}
	public void setUsername_text(String username_text) {
		this.username_text = username_text;
	}
	public LeftMenu(){
		
	}
	public LeftMenu(int imageView, String username_text) {
		this.imageView = imageView;
		this.username_text = username_text;
	}
}
