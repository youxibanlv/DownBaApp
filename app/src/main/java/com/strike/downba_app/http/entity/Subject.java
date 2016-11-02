package com.strike.downba_app.http.entity;


import com.strike.downba_app.http.UrlConfig;
import com.strike.downba_app.utils.VerifyUtils;

import java.io.Serializable;

/**
 * Created by strike on 16/8/2.
 */
public class Subject implements Serializable{
    private String area_id;
    private String title;
    private String area_type;
    private String area_html;
    private String area_logo;
    private String id_list;

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArea_type() {
        return area_type;
    }

    public void setArea_type(String area_type) {
        this.area_type = area_type;
    }

    public String getArea_html() {
        return area_html;
    }

    public void setArea_html(String area_html) {
        this.area_html = area_html;
    }

    public String getArea_logo() {
        if (!VerifyUtils.isUrl(area_logo)){
            area_logo = UrlConfig.BASE_URL + area_logo;
        }
        return area_logo;
    }

    public void setArea_logo(String area_logo) {
        this.area_logo = area_logo;
    }

    public String getId_list() {
        return id_list;
    }

    public void setId_list(String id_list) {
        this.id_list = id_list;
    }
}
