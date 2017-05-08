package com.strike.downba_app.http.bean;


import com.strike.downba_app.utils.TimeUtil;

public class Comment {

	private int id;
	private int obj_id;
	private String title;
	private int obj_type;
	private String content;
	private long update_time;
	private String user_name;
	
	private String updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getObj_id() {
		return obj_id;
	}

	public void setObj_id(int obj_id) {
		this.obj_id = obj_id;
	}

	public int getObj_type() {
		return obj_type;
	}

	public void setObj_type(int obj_type) {
		this.obj_type = obj_type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUpdateTime() {
		if (update_time < 0) {
			updateTime="";
		}else {
			updateTime = TimeUtil.longToDateStr(update_time, null);
		}
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
}
