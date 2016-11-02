package com.strike.downba_app.db.table;


import com.strike.downba_app.http.UrlConfig;
import com.strike.downba_app.utils.VerifyUtils;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by strike on 16/6/2.
 */
@Table(name = "appcms_user")
public class User {
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "uid")
    private String uid;//用户id

    @Column(name = "username")
    private String username;//账号

    @Column(name = "password")
    private String password;//密码

    @Column(name = "token")
    private String token;//令牌

    @Column(name = "phone")
    private String phone;//电话号码

    @Column(name = "nickname")
    private String nickname;//昵称

    @Column(name = "icon")
    private String icon;//头像路径

    @Column(name = "alipay")
    private String alipay;//支付宝账号

    @Column(name = "point")
    private int point;//积分点

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIcon() {
        if (icon != null && !VerifyUtils.isUrl(icon)){
            icon = UrlConfig.BASE_URL+icon;
        }
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (phone != user.phone) return false;
        if (point != user.point) return false;
        if (!uid.equals(user.uid)) return false;
        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;
        if (password != null ? !password.equals(user.password) : user.password != null)
            return false;
        if (token != null ? !token.equals(user.token) : user.token != null) return false;
        if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null)
            return false;
        if (icon != null ? !icon.equals(user.icon) : user.icon != null) return false;
        return alipay != null ? alipay.equals(user.alipay) : user.alipay == null;

    }

    @Override
    public int hashCode() {
        int result = uid.hashCode();
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (alipay != null ? alipay.hashCode() : 0);
        result = 31 * result + point;
        return result;
    }
}
