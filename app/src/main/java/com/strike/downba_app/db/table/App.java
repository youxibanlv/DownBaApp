package com.strike.downba_app.db.table;


import com.strike.downba_app.http.UrlConfig;
import com.strike.downba_app.utils.VerifyUtils;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Created by strike on 16/6/7.
 */
@Table(name = "appcms_app")
public class App implements Serializable{

    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "app_id")
    private String app_id;

    @Column(name = "last_cate_id")
    private String last_cate_id;

    @Column(name = "app_title")
    private String app_title;

    @Column(name = "app_stitle")
    private String app_stitle;

    @Column(name = "app_version")
    private String app_version;

    @Column(name = "app_update_time")
    private String app_update_time;

    @Column(name = "app_size")
    private String app_size;

    @Column(name = "app_system")
    private String app_system;

    @Column(name = "app_type")
    private String app_type;

    @Column(name = "app_logo")
    private String app_logo;

    @Column(name = "app_desc")
    private String app_desc;

    @Column(name = "app_company")
    private String app_company;

    @Column(name = "app_company_url")
    private String app_company_url;

    @Column(name = "app_tags")
    private String app_tags;

    @Column(name = "create_time")
    private String create_time;

    @Column(name = "app_grade")
    private String app_grade;

    @Column(name = "app_recomment")
    private String app_recomment;

    @Column(name = "uid")
    private String uid;

    @Column(name = "uname")
    private String uname;

    @Column(name = "app_comments")
    private String app_comments;

    @Column(name = "app_visitors")
    private String app_visitors;

    @Column(name = "app_down")
    private String app_down;

    @Column(name = "app_order")
    private String app_order;

    @Column(name = "data_app_id")
    private String data_app_id;

    @Column(name = "charge_app_id")
    private String charge_app_id;

    @Column(name = "rewrite_tag")
    private String rewrite_tag;

    @Column(name = "seo_title")
    private String seo_title;

    @Column(name = "seo_keywords")
    private String seo_keywords;

    @Column(name = "seo_desc")
    private String seo_desc;

    private List<String> resource;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getLast_cate_id() {
        return last_cate_id;
    }

    public void setLast_cate_id(String last_cate_id) {
        this.last_cate_id = last_cate_id;
    }

    public String getApp_title() {
        return app_title;
    }

    public void setApp_title(String app_title) {
        this.app_title = app_title;
    }

    public String getApp_stitle() {
        return app_stitle;
    }

    public void setApp_stitle(String app_stitle) {
        this.app_stitle = app_stitle;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getApp_update_time() {
        return app_update_time;
    }

    public void setApp_update_time(String app_update_time) {
        this.app_update_time = app_update_time;
    }

    public String getApp_size() {
        return app_size;
    }

    public void setApp_size(String app_size) {
        this.app_size = app_size;
    }

    public String getApp_system() {
        return app_system;
    }

    public void setApp_system(String app_system) {
        this.app_system = app_system;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public String getApp_logo() {
        if (!VerifyUtils.isUrl(app_logo)){
            app_logo = UrlConfig.BASE_IMG_URL+app_logo;
        }
        return app_logo;
    }
    public List<String> getResource() {
        if (resource == null){
            return null;
        }
        for (int i = 0;i<resource.size();i++){
            String url = resource.get(i);
            if (!VerifyUtils.isUrl(url)){
                url = UrlConfig.BASE_IMG_URL+resource.get(i);
            }
            resource.set(i,url);
        }
        return resource;
    }

    public void setApp_logo(String app_logo) {
        this.app_logo = app_logo;
    }

    public String getApp_desc() {
        return app_desc;
    }

    public void setApp_desc(String app_desc) {
        this.app_desc = app_desc;
    }

    public String getApp_company() {
        return app_company;
    }

    public void setApp_company(String app_company) {
        this.app_company = app_company;
    }

    public String getApp_company_url() {
        return app_company_url;
    }

    public void setApp_company_url(String app_company_url) {
        this.app_company_url = app_company_url;
    }

    public String getApp_tags() {
        return app_tags;
    }

    public void setApp_tags(String app_tags) {
        this.app_tags = app_tags;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getApp_grade() {
        return app_grade;
    }

    public void setApp_grade(String app_grade) {
        this.app_grade = app_grade;
    }

    public String getApp_recomment() {
        return app_recomment;
    }

    public void setApp_recomment(String app_recomment) {
        this.app_recomment = app_recomment;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getApp_comments() {
        return app_comments;
    }

    public void setApp_comments(String app_comments) {
        this.app_comments = app_comments;
    }

    public String getApp_visitors() {
        return app_visitors;
    }

    public void setApp_visitors(String app_visitors) {
        this.app_visitors = app_visitors;
    }

    public String getApp_down() {
        return app_down;
    }

    public void setApp_down(String app_down) {
        this.app_down = app_down;
    }

    public String getApp_order() {
        return app_order;
    }

    public void setApp_order(String app_order) {
        this.app_order = app_order;
    }

    public String getData_app_id() {
        return data_app_id;
    }

    public void setData_app_id(String data_app_id) {
        this.data_app_id = data_app_id;
    }

    public String getCharge_app_id() {
        return charge_app_id;
    }

    public void setCharge_app_id(String charge_app_id) {
        this.charge_app_id = charge_app_id;
    }

    public String getRewrite_tag() {
        return rewrite_tag;
    }

    public void setRewrite_tag(String rewrite_tag) {
        this.rewrite_tag = rewrite_tag;
    }

    public String getSeo_title() {
        return seo_title;
    }

    public void setSeo_title(String seo_title) {
        this.seo_title = seo_title;
    }

    public String getSeo_keywords() {
        return seo_keywords;
    }

    public void setSeo_keywords(String seo_keywords) {
        this.seo_keywords = seo_keywords;
    }

    public String getSeo_desc() {
        return seo_desc;
    }

    public void setSeo_desc(String seo_desc) {
        this.seo_desc = seo_desc;
    }

    public void setResource(List<String> resource) {
        this.resource = resource;
    }
}
