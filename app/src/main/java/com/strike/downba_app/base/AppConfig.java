package com.strike.downba_app.base;

import android.os.Environment;

import java.io.File;

/**
 * Created by strike on 16/5/31.
 */
public class AppConfig {

    /*****
     * 本地文件的默认存储路径
     ****/
    public static String getBasePath(){
        String BASE_PATH = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            BASE_PATH = Environment.getExternalStorageDirectory() + File.separator + "DownBaApp/";

        }else{
//            BASE_PATH = Environment.getExternalStoragePublicDirectory();
        }
        return BASE_PATH;
    }
//    public static final String BASE_PATH = Environment.getExternalStorageDirectory() + File.separator + "82down";

    // 得到照片路经
    public static final String cameraPath = getBasePath() + "/img/";
    /**
     * 日志的存储路径
     */
    public static final String LOG_PATH = getBasePath() + "/log/";
    /**
     * 下载文件的存储路径
     */
    public static final String DOWN_PATH = getBasePath() + "/apk/";
    /**
     * 默认的字符格式
     */
    /**
     * app 默认数据库
     */
    public static final String DEFAULT_APP_DB = "82down.db";
    /**
     * APP默认数据库版本号
     */
    public static final int DEFAULT_DB_VERSION = 2;
    /**
     * APP默认编码格式
     */
    public static final String DEFAULT_CHARSET = "UTF-8";
    /**
     * 是否debug调试
     */
    public static boolean DEBUG = true;

}
