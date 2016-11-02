package com.strike.downba_app.http.request;


import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/8/2.
 */
public class SubjectReq extends BaseRequest {

    RequestParam requestParams;

    public SubjectReq(int pageNo,int pageSize) {
        cmdType = "appService";
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
