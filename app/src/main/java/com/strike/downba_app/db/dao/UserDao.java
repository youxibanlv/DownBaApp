package com.strike.downba_app.db.dao;

import android.text.TextUtils;

import com.strike.downba_app.base.MyApplication;
import com.strike.downba_app.db.table.User;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

/**
 * Created by strike on 16/6/2.
 */
public class UserDao {

    public static User getUserByUserName(String userName){
        User user = null;
        if (!TextUtils.isEmpty(userName)){
            DbManager dbManager = MyApplication.getAppDb();
            try {
                user = dbManager.selector(User.class).where("username","=",userName).findFirst();
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public static void saveUser(User user){
        if (user!= null){
            DbManager dbManager = MyApplication.getAppDb();
            try {
                dbManager.delete(User.class);
                dbManager.save(user);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    public static User getUser(){
        DbManager dbManager = MyApplication.getAppDb();
        User user = null;
        try {
            user = dbManager.findFirst(User.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static String getToken(){
        String token = "";
        User user = getUser();
        if (user != null){
            token = user.getToken();
        }
        return token;
    }

    public static void logOut(){
        User user = getUser();
        user.setToken("");
        saveUser(user);
    }
}
