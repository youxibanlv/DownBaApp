package com.strike.downba_app.http.entity;


import com.strike.downba_app.db.table.App;
import com.strike.downba_app.http.UrlConfig;
import com.strike.downba_app.utils.VerifyUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by strike on 16/8/2.
 */
public class Subject implements Serializable{
    private String id;
    private String title;
    private String des;
    private String logo;
    private String idString;
    private List<App> apps;

    public Subject(String id, String title, String des, String logo, String idString, List<App> apps) {
        this.id = id;
        this.title = title;
        this.des = des;
        this.logo = logo;
        this.idString = idString;
        this.apps = apps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (!VerifyUtils.isUrl(logo) && logo != null && !"".equals(logo)){
            logo = UrlConfig.BASE_IMG_URL + logo;
        }
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public List<App> getApps() {
        return apps;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }
}
