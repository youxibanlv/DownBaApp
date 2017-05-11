package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/6/6.
 */
public class HomeBeanReq extends BaseRequest {

    RequestParam requestParams;

    public HomeBeanReq(int pageNo,int pageSize){
        cmdType = "appBusiness";
        methodName = "getHomeBeans";
        requestParams = new RequestParam(pageNo,pageSize);
    }
    class RequestParam{
        int pageNo,pageSize;

        public RequestParam(int pageNo,int pageSize){

            this.pageNo = pageNo;
            this.pageSize = pageSize;
        }
    }
}
