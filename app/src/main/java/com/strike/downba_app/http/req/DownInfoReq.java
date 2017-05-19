package com.strike.downba_app.http.req;

import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 2017/5/18.
 */

public class DownInfoReq extends BaseReq {

    public DownInfoReq(int appId, int percent, int status,String bak) {
        requestParams = new RequestParam(appId,percent,status,bak);
        cmdType = "appBusiness";
        methodName = "downloadApp";
    }

    class RequestParam {
        int appId;
        int percent;
        int status;
        String bak;

        public RequestParam(int appId, int percent, int status,String bak) {
            this.appId = appId;
            this.percent = percent;
            this.status = status;
            this.bak = bak;
        }
    }

}
