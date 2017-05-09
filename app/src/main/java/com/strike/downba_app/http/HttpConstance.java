package com.strike.downba_app.http;

/**
 * Created by strike on 16/6/2.
 */
public interface HttpConstance {

    int HTTP_SUCCESS = 0;//网络响应成功表示

    int DEFAULT_TIMEOUT = 30000;//默认超时时间

    String KEY = "YoXIB%aNlv&*St#@ike";

    /*******cmdType*******/
    int SEV_APP = 1;
    int SEV_USER = 2;
    int SEV_VERSION = 3;


    /*******methodName*******/
    int getAppHome = 0x0001;//查询首页
    int getWheel = 0x002;//查询轮播图
    int getAppDetails = 0x003;//查询app详情
    int addComment = 0x004;//发表评论
    int getComments = 0x005;//查询评论列表
}
