package com.strike.downba_app.http.entity;

import com.strike.downba_app.db.table.App;

import java.util.ArrayList;
import java.util.List;

public class HomeBean {
    public static int TYPE_NOMAL = 2;
    public static int TYPE_RECOMMEND = 1;
    public static int TYPE_SUBJECT = 3;

    private int id;
    private int homeBeanType;//首页元素类型（1为精品推荐类型，2为普通推荐类型，3为专题类型）
    private String homeBeanTitle;//首页元素标题（如 精品推荐）
    private String homeBeanLogo;//首页元素的图标url，只有 专题类型才有
    private String appIds;//app，或 资讯id的字符串，用“，”分隔
    private String updateTime;//更新时间
    private Integer sort;//排序，排序，数值越小越靠前
    private List<App> apps = new ArrayList<>();


    public HomeBean(int id, int homeBeanType, String homeBeanTitle, String homeBeanLogo, String appIds, String updateTime, int sort) {
        super();
        this.id = id;
        this.homeBeanType = homeBeanType;
        this.homeBeanTitle = homeBeanTitle;
        this.homeBeanLogo = homeBeanLogo;
        this.appIds = appIds;
        this.updateTime = updateTime;
        this.sort = sort;
    }

    public HomeBean() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHomeBeanType() {
        return homeBeanType;
    }

    public void setHomeBeanType(int homeBeanType) {
        this.homeBeanType = homeBeanType;
    }

    public String getHomeBeanTitle() {
        return homeBeanTitle;
    }

    public void setHomeBeanTitle(String homeBeanTitle) {
        this.homeBeanTitle = homeBeanTitle;
    }

    public String getHomeBeanLogo() {
        return homeBeanLogo;
    }

    public void setHomeBeanLogo(String homeBeanLogo) {
        this.homeBeanLogo = homeBeanLogo;
    }

    public String getAppIds() {
        return appIds;
    }

    public void setAppIds(String appIds) {
        this.appIds = appIds;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<App> getApps() {
        return apps;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


}
