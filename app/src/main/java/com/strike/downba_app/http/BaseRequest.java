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
public class BaseRequest {

    /**
     * 接口类型
     */
    public String cmdType = "";
    /**
     * 方法名称
     */
    public String methodName = "";
    /**
     * 令牌
     */
    public String token = "";
    /**
     * openId
     */
    public String openId = "";

    public transient RequestParams rp;


    public String getRequestData(){
        Gson gson = new Gson();
        token = UserDao.getToken();
        return gson.toJson(this);
    }

    public void sendRequest(Callback.CommonCallback<String> callback){
        LogUtil.e("url:"+UrlConfig.getUrl(cmdType));
        this.postRequest(UrlConfig.getUrl(cmdType)+methodName+".do",this.getRequestData(),callback);
    }

    private void postRequest(String url,String requestData,final Callback.CommonCallback<String> callback){
        try {
            StringBody sb = new StringBody(requestData, AppConfig.DEFAULT_CHARSET);
            rp = new RequestParams(url,null,null,null);
            rp.setRequestBody(sb);
            LogUtil.e(requestData);
            x.http().request(HttpMethod.POST,rp,callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void upLoadFile(String path,Callback.CommonCallback<String> callback){
        try {
            rp = new RequestParams(UrlConfig.getUrl(cmdType),null,null,null);
            rp.setMultipart(true);
            rp.addBodyParameter("methodName",methodName);
            rp.addBodyParameter("cmdType",cmdType);
            rp.addBodyParameter("openId",openId);
            rp.addBodyParameter("token",token);
            rp.addBodyParameter("file",new File(path));
            rp.addBodyParameter("user",UserDao.getUser().getUsername());
            x.http().request(HttpMethod.POST,rp,callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
