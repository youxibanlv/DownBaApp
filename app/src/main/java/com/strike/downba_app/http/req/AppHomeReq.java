package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 16/6/6.
 */
public class AppHomeReq extends BaseReq {

    public AppHomeReq(int pageNo){
        cmdType = "appBusiness";
        methodName = "getAppHome";
        requestParams = new RequestParam(pageNo);
    }
    public class RequestParam{
        public int pageNo;

        public RequestParam(int pageNo){
            this.pageNo = pageNo;
        }
    }
}
