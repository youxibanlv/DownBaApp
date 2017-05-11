package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 16/8/4.
 */
public class InfoReq extends BaseReq {

    public InfoReq(int pageNo,int pageSize, int cateId) {
        cmdType = "appBusiness";
        methodName = "getInfoList";
        requestParams = new RequestParam(pageNo,pageSize,cateId);
    }
    class RequestParam{
        int pageNo;
        int pageSize;
        int cateId;
        public RequestParam(int pageNo,int pageSize, int cateId){
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            this.cateId = cateId;
        }
    }
}
