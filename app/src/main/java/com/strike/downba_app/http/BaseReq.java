package com.strike.downba_app.http;


import com.google.gson.Gson;
import com.strike.downba_app.base.AppConfig;
import com.strike.downba_app.base.MyApplication;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.UiUtils;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.body.StringBody;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by strike on 16/5/31.
 */
public class BaseReq {

    /**
     * 接口类型
     */
    public String cmdType;
    /**
     * 方法名称
     */
    public String methodName;
    /**
     * 令牌
     */
    public String token = "";

    public String sign="";

    public String devId="";

    public String channelId;

    public Object requestParams;

    public transient RequestParams rp;


    public String getRequestData(){
        Gson gson = new Gson();
        devId = MyApplication.devInfo.getDevId();
        channelId = MyApplication.channelId;
        token = MyApplication.token;
        sign = HttpUtil.getSign(this);
        return gson.toJson(this);
    }

    public void sendRequest(Callback.CommonCallback<String> callback){
        if (MyApplication.devInfo.getStatus() != Constance.STATUS_NOMAL){
            UiUtils.showTipToast(false,"你的设备因非法操作，已被禁止使用");
            return;
        }
        this.postRequest(UrlConfig.BUSINESS,this.getRequestData(),callback);
    }

    private void postRequest(String url,String requestData,final Callback.CommonCallback<String> callback){
        try {
            LogUtil.e("url:"+url);
            StringBody sb = new StringBody(requestData, AppConfig.DEFAULT_CHARSET);
            rp = new RequestParams(url,null,null,null);
            rp.setRequestBody(sb);
            if (callback instanceof NormalCallBack){
                ((NormalCallBack) callback).setRequestTime(System.currentTimeMillis());
            }
            x.http().request(HttpMethod.POST,rp,callback);
            LogUtil.e(requestData);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void upLoadFile(String path,Callback.CommonCallback<String> callback){
        try {
            rp = new RequestParams(UrlConfig.UPLOAD,null,null,null);
            rp.setMultipart(true);
            rp.addBodyParameter("methodName",methodName);
            rp.addBodyParameter("cmdType",cmdType);
            rp.addBodyParameter("token",MyApplication.token);
            rp.addBodyParameter("file",new File(path));
            rp.addBodyParameter("devId",MyApplication.devInfo.getDevId());
            rp.addBodyParameter("channelId",MyApplication.channelId+"");

            Map<String,String> map = new HashMap<>();
            map.put("methodName",methodName);
            map.put("cmdType",cmdType);
            map.put("token",MyApplication.token);
            map.put("devId",MyApplication.devInfo.getDevId());
            map.put("channelId",MyApplication.channelId);
            map.put("requestParams","");

            rp.addBodyParameter("sign", HttpUtil.getSign(map));
            x.http().request(HttpMethod.POST,rp,callback);
            LogUtil.e(rp.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
