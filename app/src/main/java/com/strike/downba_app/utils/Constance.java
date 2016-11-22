package com.strike.downba_app.utils;

/**
 * Created by strike on 16/6/28.
 * <p>
 * app内部使用的常量接口
 * <p>
 * 主要用于定义app内部跳转的key
 */
public interface Constance {

    String INTENT_FLAG = "flag";//intent 携带的标示

    String KEYWORD = "keyword";//关键词

    String APP_ID = "app_id";//应用id

    String IMG = "img";

    String SUBJECT = "subject";//专题对象

    String APP = "app";

    String INFO = "info";

    String INFO_TYPE = "infoType";

    String CLIENT_SECRET = "down8app@strike%*xo";//app鉴权秘钥

    int PARENT_GAME = 2;//游戏分类
    int PARENT_APP = 1;//应用分类
    int ORDER_HOT = 1;//排行
    int ORDER_NEW = 2;//最新

    int RECOMMEND = 0;//推荐
    int SEARCH = 1;//搜索
    int cate_original = 23;//原创的数据库id
    int cate_review = 19;//评测的数据库id
    int cate_news = 22;//行业新闻的数据库id
    int cate_app = 1;//应用
    int cate_game = 2;//游戏

    String DEFAULT_CHARSET = "UTF-8";

    /*******
     * 下载相关
     **********/
    String UNKNOWN = "UNKNOWN";// 未下载
    String ACTION_START = "download_start";// 开始下载
    String ACTION_LOADING = "onloading";// 下载中
    String ACTION_COMPLETE = "download_complete";// 下载完成
    String STATE = "state";
    String CURRENT = "current";// 当前下载量
    String TOTAL = "total";// 文件大小
    String INDEX = "index";//
    String VIEW_ID = "viewId";

    int WAITTING = 4; // 应用已经安装，可以打开
    int LOADING = 1;// 应用正在下载
    int COMPLETE = 2;// 应用下载完成，还没安装
    int PAUSE = 3;// 暂停下载
    int UNKNOW = 0;// 应用未开始下载
    int FAILD = 5;// 下载失败

}
