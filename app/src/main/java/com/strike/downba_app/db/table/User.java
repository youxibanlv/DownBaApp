package com.strike.downba_app.db.table;


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by strike on 16/6/2.
 */
@Table(name = "appcms_user")
public class User {

    @Column(name = "user_id",isId = true)
    private String user_id;//用户id

    @Column(name = "user_name")
    private String user_name;//账号

    @Column(name = "nickname")
    private String nickname;//密码

    @Column(name = "phone_num")
    private String phone_num;//令牌

    @Column(name = "register_time")
    private String register_time;//电话号码

    @Column(name = "user_email")
    private String user_email;//昵称

    @Column(name = "user_icon")
    private String user_icon;//头像路径

    @Column(name = "user_point")
    private String user_point;//支付宝账号

    @Column(name = "vip_name")
    private int vip_name;//积分点
    @Column(name = "address")
    private long address;//注册时间
    @Column(name = "update_time")
    private long update_time;//更新时间

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public String getUser_point() {
        return user_point;
    }

    public void setUser_point(String user_point) {
        this.user_point = user_point;
    }

    public int getVip_name() {
        return vip_name;
    }

    public void setVip_name(int vip_name) {
        this.vip_name = vip_name;
    }

    public long getAddress() {
        return address;
    }

    public void setAddress(long address) {
        this.address = address;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }
}
