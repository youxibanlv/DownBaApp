package com.strike.downba_app.http.bean;


import android.text.TextUtils;

import com.strike.downba_app.http.UrlConfig;
import com.strike.downba_app.utils.VerifyUtils;

import java.io.Serializable;

public class AppAd implements Serializable {

	private Integer ad_id;
	private Integer obj_id;
	private Integer obj_type;
	private String logo;

	private AppInfo app;
	private Info info;
	private Subject subject;


	public AppAd() {
	}


	public Integer getAd_id() {
		return ad_id;
	}


	public void setAd_id(Integer ad_id) {
		this.ad_id = ad_id;
	}


	public Integer getObj_id() {
		return obj_id;
	}


	public void setObj_id(Integer obj_id) {
		this.obj_id = obj_id;
	}


	public Integer getObj_type() {
		return obj_type;
	}


	public void setObj_type(Integer obj_type) {
		this.obj_type = obj_type;
	}


	public String getLogo() {
		if (TextUtils.isEmpty(logo)) return "";
		if (!VerifyUtils.isUrl(logo)){
			logo = UrlConfig.BASE_IMG_URL+logo.trim();
		}
		return logo;
	}


	public void setLogo(String logo) {
		this.logo = logo;
	}


	public AppInfo getApp() {
		return app;
	}


	public void setApp(AppInfo app) {
		this.app = app;
	}


	public Info getInfo() {
		return info;
	}


	public void setInfo(Info info) {
		this.info = info;
	}


	public Subject getSubject() {
		return subject;
	}


	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	
}
