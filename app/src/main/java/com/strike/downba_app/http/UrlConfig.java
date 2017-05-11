package com.strike.downba_app.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by strike on 16/5/31.
 */
public class UrlConfig {

    public static final Map<String,String> URL_MAP = new HashMap<>();

    /***
     * 服务器地址
     * **/
//    public static String BASE_URL = "http://192.168.1.111:8080/DownBaWeb/mobile/dispatcher.do";//家里
    public static String BASE_URL = "http://192.168.206.54:8080/DownBaWeb/mobile/dispatcher.do";//公司
//    public static String BASE_URL = "http://123.57.86.113:8081/DownBaWeb/mobile/dispatcher.do";//外网

//      public static String BASE_IMG_URL = "http://192.168.1.111:8080";//家里
//    public static String BASE_IMG_URL = "http://123.57.86.113:8081";//外网
    public static String BASE_IMG_URL = "http://192.168.206.54:8080";//公司
    public static  String WEB_URL = "http://www.82down.com";

    //用户相关
    public static String USER_SERVICE = BASE_URL+"/userService/";
    //应用香港
    public static String APP_SERVICE = BASE_URL+"/mobile/";//app相关
    //上传文件
    public static String UPLOAD_SERVICE = BASE_URL+"/app/upload_file.php";
    //更新应用
    public static String UPDATE_SERVICE = BASE_URL+"/versionService/";//更新相关


    static {
        URL_MAP.put("userService",USER_SERVICE);
        URL_MAP.put("appService",APP_SERVICE);
        URL_MAP.put("versionService",UPDATE_SERVICE);
        URL_MAP.put("upload",UPLOAD_SERVICE);
    }


    public static String getUrl(String cmdType){
        return URL_MAP.get(cmdType);
    }

}
