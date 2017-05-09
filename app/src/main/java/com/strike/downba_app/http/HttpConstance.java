package com.strike.downba_app.http;

import java.util.HashMap;

/**
 * Created by strike on 16/6/2.
 */
public class HttpConstance {

    public static final int HTTP_SUCCESS = 0;//网络响应成功表示

    public static final int DEFAULT_TIMEOUT = 30000;//默认超时时间

    public static final String KEY = "YoXIB%aNlv&*St#@ike";

    /*******cmdType*******/
    public static final int SEV_APP = 1;
    public static final int SEV_USER = 2;
    public static final int SEV_VERSION = 3;


    /*******methodName*******/
    public static final int getAppHome = 0x0001;//查询首页
    public static final int getWheel = 0x002;//查询轮播图
    public static final int getAppDetails = 0x003;//查询app详情
    public static final int addComment = 0x004;//发表评论
    public static final int getComments = 0x005;//查询评论列表


    public static final HashMap<String,Integer> cmdMap = new HashMap<>();
    static {
        cmdMap.put("appBusiness",SEV_APP);
    }


    public static final HashMap<String,Integer> methodMap = new HashMap<>();

    static {
        methodMap.put("getAppHome",getAppHome);
        methodMap.put("getWheel",getWheel);
        methodMap.put("getAppDetails",getAppDetails);
        methodMap.put("addComment",addComment);
        methodMap.put("getComments",getComments);
    }
}
