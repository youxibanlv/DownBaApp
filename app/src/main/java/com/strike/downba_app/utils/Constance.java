package com.strike.downba_app.utils;

/**
 * Created by strike on 16/6/28.
 *
 * app内部使用的常量接口
 *
 * 主要用于定义app内部跳转的key
 */
public interface Constance {

    int ORDER_TYPE_SORT = 1;//sort 字段排序
    int ORDER_TYPE_TIME = 2;//时间排序

    int CATE_APP = 1;//软件分类
    int CATE_GAME = 2;//游戏分类
    int CATE_INFO = 21;//资讯分类

    int SB_ONE_APP = 1;//专题类型为单个app
    int SB_LIST_APP = 2;//专题类型为app列表
    int SB_APP_INFO = 3;//专题类型为一个app和与之相关的资讯列表，如（攻略之类的）

    int AD_SLIDE = 1;//幻灯片，出现在首页顶端
    int AD_BANNER = 2;//banner，出现在列表中
    int AD_INFO = 3;//资讯类广告
    int AD_RECOMD = 4;//精品推荐
    int AD_APP = 5;//app+简介
    int AD_GUESS_YOU_LIKE = 6;//猜你喜欢


    int AD_OBJ_APP = 1;//app类广告
    int AD_OBJ_SB = 2;//专题类广告
    int AD_OBJ_INFO = 3;//资讯类广告

    int ADTYPE_TIME =1;//为限时投放（到期自动下架）
    int ADTYPE_VISITOR = 2;//为点击量投放（到达点击量自动下架）
    int ADTYPE_VIEW = 3;//曝光量投放（达到曝光量自动下架）

    int ONLINE = 1;//在线
    int OFFLINE = 0;//下线

    String INTENT_FLAG = "flag";//intent 携带的标示

    String KEYWORD = "keyword";//关键词

    String APP_ID = "app_id";//应用id

    String IMG = "img";

    String SUBJECT = "subject";//专题对象

    String APP = "app";

    String INFO = "info";

    String INFO_TYPE = "infoType";

    String CLIENT_SECRET ="down8app@strike%*xo" ;//app鉴权秘钥

    String CATE ="cate";

    int PARENT_GAME = 2;//游戏分类
    int PARENT_APP = 1;//应用分类
    int ORDER_HOT= 1;//排行
    int ORDER_NEW = 2;//最新

    int RECOMMEND = 0;//推荐
    int SEARCH = 1;//搜索
    int cate_original = 23;//原创的数据库id
    int cate_review = 19;//评测的数据库id
    int cate_news = 22;//行业新闻的数据库id
    int cate_app = 1;//应用
    int cate_game= 2;//游戏

    String DEFAULT_CHARSET = "UTF-8";

    /****下载相关****/
    String LISTVIEW_ID = "listViewId";
    String POSITION = "position";
    String ACTION_DOWNLOAD = "action_download";
    String STATE = "state";
    String PROGRESS = "progress";
    int LOADING = 1;
    int COMPLETE = 2;
    int PAUSE = 3;
    int WAITTING = 4;

    int DES_LENGTH=66;
}
