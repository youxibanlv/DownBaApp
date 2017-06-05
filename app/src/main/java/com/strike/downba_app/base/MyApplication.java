package com.strike.downba_app.base;

import android.app.Application;
import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.strike.downba_app.db.DbConfig;
import com.strike.downba_app.db.dao.UserDao;
import com.strike.downba_app.http.bean.DevInfo;
import com.strike.downba_app.utils.AppUtils;
import com.strike.downba_app.utils.CrashHandler;
import com.strike.downba_app.utils.PhoneInfoUtils;

import org.xutils.DbManager;
import org.xutils.common.util.LogUtil;
import org.xutils.x;

/**
 * Created by strike on 16/5/31.
 */
public class MyApplication extends Application {

    public static DbManager appDb = null;

    public static DevInfo devInfo;

    public static String channelId;

    public static String token;

    public static double longitude = 0;//精度

    public static double latitude = 0;//纬度


    public static Context context;

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient;

    private AMapLocationClientOption mLocationOption;

    private AMapLocationListener mLocationListener;

    public static DbManager getAppDb() {
        if (appDb == null) {
            appDb = x.getDb(DbConfig.APP_DB_CONFIG);
        }
        return appDb;
    }

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
        setLocation();
    }

    private void setLocation() {
        mLocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        mLocationOption.setOnceLocation(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        latitude = aMapLocation.getLatitude();
                        longitude = aMapLocation.getLongitude();
                        mLocationClient.stopLocation();
                    } else {
                        LogUtil.e("location Error, ErrCode:" + aMapLocation.getErrorCode()
                                + ", errInfo:" + aMapLocation.getErrorInfo());
                    }
                }
            }
        };
        mLocationClient.setLocationListener(mLocationListener);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }
}
