package com.strike.downba_app.http.request;


import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/6/29.
 */
public class AppDetailsReq extends BaseRequest {
    public RequestParam requestParams ;
    public AppDetailsReq(String app_id){
        cmdType = "appService";
        methodName = "getAppDetails";
        requestParams = new RequestParam(app_id);
    }

    public class RequestParam{
        public String app_id;
        public RequestParam(String appId){
            this.app_id = appId;
        }
    }
}
