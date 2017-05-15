package com.strike.downba_app.http;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.USER_SERVICE;

/**
 * Created by strike on 16/5/31.
 */
public class UrlConfig {

    public static final Map<String,String> URL_MAP = new HashMap<>();

    /***
     * 服务器地址
     * **/
//    public static String BASE_URL = "http://192.168.1.111:8080";//家里
    public static String BASE_URL = "http://192.168.206.12:8080";//公司
//    public static String BASE_URL = "http://123.57.86.113:8081";//外网


    //基础业务
    public static String BUSINESS = BASE_URL+"/DownBaWeb/mobile/dispatcher.do";
    //上传文件
    public static String UPLOAD = BASE_URL+"/DownBaWeb/mobile/upload.do";
    //更新应用
    public static String UPDATE_SERVICE = BASE_URL+"/versionService/";//更新相关


    static {
        URL_MAP.put("userService",USER_SERVICE);
        URL_MAP.put("versionService",UPDATE_SERVICE);
    }


    public static String getUrl(String cmdType){
        return URL_MAP.get(cmdType);
    }

}
