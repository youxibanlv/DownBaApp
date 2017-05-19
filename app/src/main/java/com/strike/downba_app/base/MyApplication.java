package com.strike.downba_app.base;

import android.app.Application;
import android.content.Context;

import com.strike.downba_app.db.DbConfig;
import com.strike.downba_app.db.dao.UserDao;
import com.strike.downba_app.http.bean.DevInfo;
import com.strike.downba_app.utils.AppUtils;
import com.strike.downba_app.utils.CrashHandler;
import com.strike.downba_app.utils.PhoneInfoUtils;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by strike on 16/5/31.
 */
public class MyApplication extends Application {

    public static DbManager appDb = null;

    public static DevInfo devInfo;

    public static String channelId;

    public static String token;

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化xutils
        AppContext.setContext(this);
        x.Ext.init(this);
        x.Ext.setDebug(true);
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());
        devInfo = PhoneInfoUtils.getDevInfo(this);
        channelId = String.valueOf(AppUtils.getLocalVersion(this).getChannel_id());
        token = UserDao.getToken();
        context = this;
        AppContext.setContext(context);
    }

    public static DbManager getAppDb() {
        if (appDb == null) {
            appDb = x.getDb(DbConfig.APP_DB_CONFIG);
        }
        return appDb;
    }
}
