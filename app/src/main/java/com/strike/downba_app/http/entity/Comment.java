package com.strike.downba_app.http.entity;

public class Comment {
    private int comment_id =0;
    private int id=0;
    private int type=0;
    private String content="";
    private long date_add=0;
    private int uid=0;
    private String uname="";
    private String ip="";
    private String ip_addr="";
    private int parent_id=0;
    private int is_check=0;
    private int son=0;
    private int good=0;
    private int bad=0;

    public Comment(int comment_id, int id, int type, String content, long date_add, int uid, String uname, String ip, String ip_addr,
                   int parent_id, int is_check, int son, int good, int bad) {
        super();
        this.comment_id = comment_id;
        this.id = id;
        this.type = type;
        this.content = content;
        this.date_add = date_add;
        this.uid = uid;
        this.uname = uname;
        this.ip = ip;
        this.ip_addr = ip_addr;
        this.parent_id = parent_id;
        this.is_check = is_check;
        this.son = son;
        this.good = good;
        this.bad = bad;
    }

    public Comment() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp_addr() {
        return ip_addr;
    }

    public void setIp_addr(String ip_addr) {
        this.ip_addr = ip_addr;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getIs_check() {
        return is_check;
    }

    public void setIs_check(int is_check) {
        this.is_check = is_check;
    }

    public int getSon() {
        return son;
    }

    public void setSon(int son) {
        this.son = son;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getBad() {
        return bad;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }


    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public long getDate_add() {
        return date_add;
    }

    public void setDate_add(long date_add) {
        this.date_add = date_add;
    }
}
