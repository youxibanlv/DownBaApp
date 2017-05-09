package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 16/6/29.
 */
public class AppDetailsReq extends BaseReq {
    public AppDetailsReq(int appId){
        cmdType = "appBusiness";
        methodName = "getAppDetails";
        requestParams = new RequestParam(appId);
    }

    public class RequestParam{
        public int appId;
        public RequestParam(int appId){
            this.appId = appId;
        }
    }
}
