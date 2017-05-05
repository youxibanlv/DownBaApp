package com.strike.downba_app.http.bean;

import com.strike.downba_app.http.entity.Info;
import com.strike.downba_app.utils.TimeUtil;

import java.util.List;

public class Subject {

	private Integer sb_id;
	private Integer sb_type=-1;
	private String obj_ids="";
	private Integer obj_id=-1;
	private String title="";
	private String des="";
	private String logo="";
	private long update_time=0;

	private AppInfo app;
	private List<AppInfo> apps;
	private List<Info> infos;
	private String dateTime;

	public Subject() {
	}

	public Subject(Integer sb_type, String obj_ids, Integer obj_id, String title, String des,
			String logo) {
		super();
		this.sb_type = sb_type;
		this.obj_ids = obj_ids;
		this.obj_id = obj_id;
		this.title = title;
		this.des = des;
		this.logo = logo;
		this.update_time = System.currentTimeMillis();
	}

	public Integer getSb_id() {
		return sb_id;
	}

	public void setSb_id(Integer sb_id) {
		this.sb_id = sb_id;
	}

	public Integer getSb_type() {
		return sb_type;
	}

	public void setSb_type(Integer sb_type) {
		this.sb_type = sb_type;
	}

	public String getObj_ids() {
		return obj_ids;
	}

	public void setObj_ids(String obj_ids) {
		this.obj_ids = obj_ids;
	}

	public Integer getObj_id() {
		return obj_id;
	}

	public void setObj_id(Integer obj_id) {
		this.obj_id = obj_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

	public List<AppInfo> getApps() {
		return apps;
	}

	public void setApps(List<AppInfo> apps) {
		this.apps = apps;
	}

	public List<Info> getInfos() {
		return infos;
	}

	public void setInfos(List<Info> infos) {
		this.infos = infos;
	}

	public String getDateTime() {
		if (update_time <= 0) {
			dateTime = "";
		} else {
			dateTime = TimeUtil.longToDateStr(update_time, null);
		}
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public AppInfo getApp() {
		return app;
	}

	public void setApp(AppInfo app) {
		this.app = app;
	}

}
