package com.strike.downba_app.http.bean;

import com.strike.downba_app.http.UrlConfig;
import com.strike.downba_app.utils.TimeUtil;
import com.strike.downba_app.utils.VerifyUtils;

import java.util.ArrayList;
import java.util.List;


public class AppInfo {

	private Integer id;
	private Integer app_id;
	private Integer cate_id;
	private String app_title="";
	private String app_version="";
	private long update_time;
	private String app_seo="";
	private String app_size="0";
	private String app_logo="";
	private String app_des="";
	private Integer app_grade;
	private Integer app_visitors;
	private Integer app_down;
	private Integer app_sort;
	private String url="";//下载地址

	private ArrayList<String> res;//资源地址
	private List<Comment> comments;
    private int totalComment;
	
	private String cateName="";
	private String dateStr="";
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getApp_id() {
		return app_id;
	}
	public void setApp_id(Integer app_id) {
		this.app_id = app_id;
	}
	public Integer getCate_id() {
		return cate_id;
	}
	public void setCate_id(Integer cate_id) {
		this.cate_id = cate_id;
	}
	public String getApp_title() {
		return app_title;
	}
	public void setApp_title(String app_title) {
		this.app_title = app_title;
	}
	public String getApp_version() {
		return app_version;
	}
	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}
	public long getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}
	public String getApp_seo() {
		return app_seo;
	}
	public void setApp_seo(String app_seo) {
		this.app_seo = app_seo;
	}
	
	public String getApp_logo() {
		if (!VerifyUtils.isUrl(app_logo)){
			app_logo = UrlConfig.BASE_URL+app_logo;
		}
		return app_logo;
	}
	public void setApp_logo(String app_logo) {
		this.app_logo = app_logo;
	}
	public String getApp_des() {
		return app_des;
	}
	public void setApp_des(String app_des) {
		this.app_des = app_des;
	}
	public Integer getApp_grade() {
		return app_grade;
	}
	public void setApp_grade(Integer app_grade) {
		this.app_grade = app_grade;
	}
	public Integer getApp_visitors() {
		return app_visitors;
	}
	public void setApp_visitors(Integer app_visitors) {
		this.app_visitors = app_visitors;
	}
	public Integer getApp_down() {
		return app_down;
	}
	public void setApp_down(Integer app_down) {
		this.app_down = app_down;
	}
	public Integer getApp_sort() {
		return app_sort;
	}
	public void setApp_sort(Integer app_sort) {
		this.app_sort = app_sort;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getDateStr() {
		if (update_time > 1) {
			dateStr = TimeUtil.longToDateStr(update_time, null);
		}else {
			dateStr = "";
		}
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getApp_size() {
		return app_size;
	}

	public void setApp_size(String app_size) {
		this.app_size = app_size;
	}

	public ArrayList<String> getRes() {
		return res;
	}

	public void setRes(ArrayList<String> res) {
		this.res = res;
	}

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }
}
