package com.strike.downba_app.http.entity;

/**
 * Created by strike on 16/6/12.
 */
public class Keyword {
    private int id;
    private String q;//搜索关键字
    private Integer qnum;//搜索次数
    private int qorder;//关键字排序

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Integer getQnum() {
        return qnum;
    }

    public void setQnum(Integer qnum) {
        this.qnum = qnum;
    }

    public int getQorder() {
        return qorder;
    }

    public void setQorder(int qorder) {
        this.qorder = qorder;
    }
}
