package com.strike.downba_app.http.bean;

import java.util.ArrayList;
import java.util.List;

public class AppHome {

	private Integer id;
	private String title1;
	private String title2;
	private long update_time;
	private Integer sort;

	private List<AppAd> recomd = new ArrayList<>();
	private List<AppAd> appAdApp = new ArrayList<>();
	private AppAd banner;

	
	public AppHome() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<AppAd> getRecomd() {
		return recomd;
	}

	public void setRecomd(List<AppAd> recomd) {
		this.recomd = recomd;
	}

	public List<AppAd> getAppAdApp() {
		return appAdApp;
	}

	public void setAppAdApp(List<AppAd> appAdApp) {
		this.appAdApp = appAdApp;
	}

	public AppAd getBanner() {
		return banner;
	}

	public void setBanner(AppAd banner) {
		this.banner = banner;
	}
}
