package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 16/8/2.
 */
public class SubjectReq extends BaseReq {

    public SubjectReq(int pageNo,int pageSize) {
        cmdType = "appBusiness";
        methodName = "getSubject";
        requestParams = new RequestParam(pageNo,pageSize);
    }
    class RequestParam{
        int pageNo;
        int pageSize;
        public RequestParam(int pageNo,int pageSize){
            this.pageNo = pageNo;
            this.pageSize = pageSize;
        }
    }
}
