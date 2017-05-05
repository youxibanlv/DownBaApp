package com.strike.downba_app.http;


import com.strike.downba_app.base.AppConfig;
import com.strike.downba_app.db.dao.UserDao;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.body.StringBody;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;

import gson.Gson;

/**
 * Created by strike on 16/5/31.
 */
public class BaseReq {

    /**
     * 接口类型
     */
    public int cmdType;
    /**
     * 方法名称
     */
    public int methodName;
    /**
     * 令牌
     */
    public String token = "";

    public String sign="";

    public Object requestParams;

    public transient RequestParams rp;


    public String getRequestData(){
        Gson gson = new Gson();
        sign = HttpUtil.getSign(this);
        return gson.toJson(this);
    }

    public void sendRequest(Callback.CommonCallback<String> callback){
        this.postRequest(UrlConfig.BASE_URL,this.getRequestData(),callback);
    }

    private void postRequest(String url,String requestData,final Callback.CommonCallback<String> callback){
        try {
            LogUtil.e("url:"+url);
            StringBody sb = new StringBody(requestData, AppConfig.DEFAULT_CHARSET);
            rp = new RequestParams(url,null,null,null);
            rp.setRequestBody(sb);
            LogUtil.e(requestData);
            if (callback instanceof NormalCallBack){
                ((NormalCallBack) callback).setRequestTime(System.currentTimeMillis());
            }
            x.http().request(HttpMethod.POST,rp,callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void upLoadFile(String path,Callback.CommonCallback<String> callback){
        try {
            rp = new RequestParams(UrlConfig.BASE_URL,null,null,null);
            rp.setMultipart(true);
            rp.addBodyParameter("methodName",String.valueOf(methodName));
            rp.addBodyParameter("cmdType",String.valueOf(cmdType));
            rp.addBodyParameter("token",token);
            rp.addBodyParameter("file",new File(path));
            rp.addBodyParameter("user",UserDao.getUser().getUid());
            x.http().request(HttpMethod.POST,rp,callback);
            LogUtil.e(rp.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
