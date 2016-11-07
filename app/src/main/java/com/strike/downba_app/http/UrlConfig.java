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
//    public static String BASE_URL = "http://192.168.121.106:8080/DownBa_web";//tp_link_youxi
//    public static String BASE_URL = "http://192.168.206.17:8080/DownBa_web";//公司
    public static String BASE_URL = "http://123.57.86.113:8081/DownBa_web";//外网

    //用户相关
    public static String USER_SERVICE = BASE_URL+"/app/userService.php";
    //应用香港
    public static String APP_SERVICE = BASE_URL+"/appService/";
    //上传文件
    public static String UPLOAD_SERVICE = BASE_URL+"/app/upload_file.php";


    static {
        URL_MAP.put("userService",USER_SERVICE);
        URL_MAP.put("appService",APP_SERVICE);
        URL_MAP.put("upload",UPLOAD_SERVICE);
    }


    public static String getUrl(String cmdType){
        return URL_MAP.get(cmdType);
    }

}