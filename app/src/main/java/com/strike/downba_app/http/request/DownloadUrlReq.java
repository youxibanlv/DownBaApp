package com.strike.downba_app.http.request;


import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/6/20.
 */
public class DownloadUrlReq extends BaseRequest {

    RequestParam requestParams;

    public DownloadUrlReq(String app_id,String version,String uid){
        cmdType = "appService";
        methodName = "getUrlById";
        requestParams = new RequestParam(app_id,version,uid);
    }

    class RequestParam{
        String app_id;
        String app_version;
        String uid;
        public RequestParam(String name,String version,String uid){
            this.app_id = name;
            this.app_version = version;
            this.uid = uid;
        }
    }
}
