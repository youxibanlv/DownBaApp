package com.strike.downba_app.http.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 2017/6/7.
 */

public class Home {
    private int objType;
    private String title;

    private List<AppAd> recomd = new ArrayList<>();
    private List<AppAd> appAdApp = new ArrayList<>();
    private AppAd banner;

    public int getObjType() {
        return objType;
    }

    public void setObjType(int objType) {
        this.objType = objType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
