package com.strike.downba_app.http.bean;

import com.strike.downba_app.http.UrlConfig;
import com.strike.downba_app.utils.VerifyUtils;

import java.io.Serializable;

/**
 * Created by strike on 16/7/2.
 *
 * 分类的实体对象
 */
public class Cate implements Serializable{
    private Integer id;
    private Integer cate_id;
    private Integer parent_id;
    private String cname;
    private String cimg;
    private Integer type;
    private String parentName;
    private long update_time;

    public Integer getCate_id() {
        return cate_id;
    }
    public void setCate_id(Integer cate_id) {
        this.cate_id = cate_id;
    }
    public Integer getParent_id() {
        return parent_id;
    }
    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public String getCimg() {
        if (!VerifyUtils.isUrl(cimg)){
            cimg = UrlConfig.BASE_URL+cimg;
        }
        return cimg;
    }
    public void setCimg(String cimg) {
        this.cimg = cimg;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public String getParentName() {
        return parentName;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public long getUpdate_time() {
        return update_time;
    }
    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
