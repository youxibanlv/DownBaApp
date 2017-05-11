package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 16/8/2.
 */
public class SbDetailsReq extends BaseReq {

    public SbDetailsReq(int sbId) {
        cmdType = "appBusiness";
        methodName = "getSbDetails";
        requestParams = new RequestParam(sbId);
    }
    class RequestParam{
        int sbId;
        public RequestParam(int sbId){
            this.sbId = sbId;
        }
    }
}
