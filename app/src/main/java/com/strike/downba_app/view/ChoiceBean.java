package com.strike.downba_app.view;

public class ChoiceBean {
	private int img;
	private String text;

	public ChoiceBean() {
	}

	public ChoiceBean(int img, String text) {
		this.img = img;
		this.text = text;
	}

	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
