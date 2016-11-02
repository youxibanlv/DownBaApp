package com.strike.downba_app.http.entity;


import com.strike.downba_app.http.UrlConfig;
import com.strike.downba_app.utils.VerifyUtils;

import java.io.Serializable;

/**
 * Created by strike on 16/8/4.
 */
public class Info implements Serializable{
    private int info_id;
    private int parent_id;
    private int last_cate_id;
    private String info_title;
    private String info_img;
    private String info_body;
    private int info_visitors;

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getLast_cate_id() {
        return last_cate_id;
    }

    public void setLast_cate_id(int last_cate_id) {
        this.last_cate_id = last_cate_id;
    }

    public String getInfo_title() {
        return info_title;
    }

    public void setInfo_title(String info_title) {
        this.info_title = info_title;
    }

    public String getInfo_img() {
        if (!VerifyUtils.isUrl(info_img)){
            info_img = UrlConfig.BASE_URL + info_img;
        }
        return info_img;
    }

    public void setInfo_img(String info_img) {
        this.info_img = info_img;
    }

    public String getInfo_body() {
        return info_body;
    }

    public void setInfo_body(String info_body) {
        this.info_body = info_body;
    }

    public int getInfo_visitors() {
        return info_visitors;
    }

    public void setInfo_visitors(int info_visitors) {
        this.info_visitors = info_visitors;
    }
}
