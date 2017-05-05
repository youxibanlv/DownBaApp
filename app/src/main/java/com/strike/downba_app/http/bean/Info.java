package com.strike.downba_app.http.bean;


import com.strike.downba_app.utils.TimeUtil;

public class Info {
	private Integer id;
	private Integer info_id=0;
	private Integer cate_id=0;
	private String cateName="";
	private String info_title="";
	private String seo="";
	private String info_logo="";
	private String info_desc="";
	private String info_body="";
	private long update_time=0;
	private String info_from="";
	private Integer info_visitors=0;
	private String dateTime="";
	
	public Info() {
	}
	public Info(Integer id, Integer info_id, Integer cate_id, String info_title, String seo, String info_logo,
			String info_desc, String info_body, String info_from, Integer info_visitors) {
		super();
		this.id = id;
		this.info_id = info_id;
		this.cate_id = cate_id;
		this.info_title = info_title;
		this.seo = seo;
		this.info_logo = info_logo;
		this.info_desc = info_desc;
		this.info_body = info_body;
		this.info_from = info_from;
		this.info_visitors = info_visitors;
		this.update_time = System.currentTimeMillis();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInfo_id() {
		return info_id;
	}
	public void setInfo_id(Integer info_id) {
		this.info_id = info_id;
	}
	public Integer getCate_id() {
		return cate_id;
	}
	public void setCate_id(Integer cate_id) {
		this.cate_id = cate_id;
	}
	public String getInfo_title() {
		return info_title;
	}
	public void setInfo_title(String info_title) {
		this.info_title = info_title;
	}
	public String getInfo_logo() {
		return info_logo;
	}
	public void setInfo_logo(String info_logo) {
		this.info_logo = info_logo;
	}
	public String getInfo_desc() {
		return info_desc;
	}
	public void setInfo_desc(String info_desc) {
		this.info_desc = info_desc;
	}
	public String getInfo_body() {
		return info_body;
	}
	public void setInfo_body(String info_body) {
		this.info_body = info_body;
	}
	public long getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}
	public String getInfo_from() {
		return info_from;
	}
	public void setInfo_from(String info_from) {
		this.info_from = info_from;
	}
	public Integer getInfo_visitors() {
		return info_visitors;
	}
	public void setInfo_visitors(Integer info_visitors) {
		this.info_visitors = info_visitors;
	}
	public String getSeo() {
		return seo;
	}
	public void setSeo(String seo) {
		this.seo = seo;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getDateTime() {
		if (update_time>0) {
			dateTime = TimeUtil.longToDateStr(update_time, null);
		}else {
			dateTime = "";
		}
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	
}
