package com.strike.downba_app.http.bean;

public class Resource {
	
	public static final int TYPE_APP = 0;
	public static final int TYPE_INFO = 1;
	public static final int TYPE_USER_ICON = 2;
	public static final int TYPE_TASK = 3;

	public static final int TYPE_APK = 0;
	public static final int TYPE_IMG = 1;
	
	private Integer id;
	private Integer obj_id;
	private Integer obj_type;
	private Integer type;
	private String url;
	private long update_time;
	
	public Resource() {
	}
	
	public Resource(Integer obj_id, Integer obj_type, Integer type, String url) {
		super();
		this.obj_id = obj_id;
		this.obj_type = obj_type;
		this.type = type;
		this.url = url;
		this.update_time = System.currentTimeMillis();
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getUpdate() {
		return update_time;
	}
	public void setUpdate(long update) {
		this.update_time = update;
	}
	
	
}
