package com.strike.downba_app.base;

import android.app.Application;

import com.strike.downba_app.db.DbConfig;
import com.strike.downba_app.utils.CrashHandler;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by strike on 16/5/31.
 */
public class MyApplication extends Application {

    public static DbManager appDb = null;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化xutils
        AppContext.setContext(this);
        x.Ext.init(this);
        x.Ext.setDebug(true);
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());
    }

    public static DbManager getAppDb() {
        if (appDb == null) {
            appDb = x.getDb(DbConfig.APP_DB_CONFIG);
        }
        return appDb;
    }
}
