package com.strike.downba_app.utils;

/**
 * Created by strike on 16/6/28.
 *
 * app内部使用的常量接口
 *
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

    String CLIENT_SECRET ="down8app@strike%*xo" ;//app鉴权秘钥

    int PARENT_GAME = 2;//游戏分类
    int PARENT_APP = 1;//应用分类
    int ORDER_HOT= 1;//排行
    int ORDER_NEW = 2;//最新

    int RECOMMEND = 0;//推荐
    int SEARCH = 1;//搜索
    int cate_original = 23;//原创的数据库id
    int cate_review = 19;//评测的数据库id
    int cate_news = 22;//行业新闻的数据库id

    String DEFAULT_CHARSET = "UTF-8";
}
