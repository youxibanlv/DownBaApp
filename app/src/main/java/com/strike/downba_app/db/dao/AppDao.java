package com.strike.downba_app.db.dao;


import com.strike.downba_app.base.MyApplication;
import com.strike.downba_app.db.table.App;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by strike on 16/6/7.
 */
public class AppDao {

    public static void saveApp(App app){
        DbManager dbManager = MyApplication.getAppDb();
        try {
            dbManager.save(app);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static void saveAppList(List<App> appList){
        if (appList != null && appList.size()>0){
            DbManager dbManager = MyApplication.getAppDb();
            //先清空数据库，再保存
            try {
                dbManager.delete(App.class);
                for(App app :appList){
                    saveApp(app);
                }
            } catch (DbException e) {
                e.printStackTrace();
            }

        }
    }

    public static App getAppByAppId(String appId){
        App app = null;
        DbManager dbManager = MyApplication.getAppDb();
        try {
            dbManager.selector(App.class).where("appId","=",appId).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return app;
    }
}
