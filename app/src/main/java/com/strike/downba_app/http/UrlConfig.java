package com.strike.downba_app.http;

/**
 * Created by strike on 16/5/31.
 */
public class UrlConfig {

    /***
     * 服务器地址
     * **/
    public static String BASE_URL = "http://192.168.1.111:8080";//家里
//    public static String BASE_URL = "http://192.168.206.12:8080";//公司
//    public static String BASE_URL = "http://123.57.86.113:8081";//外网 172.16.9.35


    //基础业务
    static String BUSINESS = BASE_URL+"/DownBaWeb/mobile/dispatcher.do";
    //上传文件
    static String UPLOAD = BASE_URL+"/DownBaWeb/mobile/file.do";
}
