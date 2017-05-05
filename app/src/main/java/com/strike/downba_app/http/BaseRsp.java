package com.strike.downba_app.http;

import android.text.TextUtils;

import com.strike.downba_app.utils.UiUtils;

import org.xutils.common.util.LogUtil;

import gson.Gson;

/**
 * Created by strike on 16/5/31.
 */
public class BaseRsp {
    public int result;
    public String failReason;
    public int cmdType;
    public int methodName;

    public static BaseRsp getRsp(String result, Class resultClass){
        BaseRsp rsp = new BaseRsp();
        if (!TextUtils.isEmpty(result)){
            Gson gson = new Gson();
            try{
                if (resultClass == BaseRsp.class || BaseRsp.class == resultClass.getSuperclass()){
                    rsp = (BaseRsp) gson.fromJson(result,resultClass);
                }
            }catch (Exception e){
                try {
                    Class c = Class.forName(resultClass.getName());
                    rsp = (BaseRsp) c.newInstance();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }finally {
                if (rsp == null){
                    rsp = new BaseRsp();
                }
                if (rsp.result != HttpConstance.HTTP_SUCCESS){//提示错误信息
                    if (rsp.failReason != null && !"".equals(rsp.failReason)){
                        UiUtils.showTipToast(false,rsp.failReason);
                    }
                }
                LogUtil.e(result);
            }
        }
        return rsp;
    }
}