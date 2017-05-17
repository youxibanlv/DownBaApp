package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 16/6/13.
 */
public class GetAppByKeywordReq extends BaseReq {


    public GetAppByKeywordReq(String key,int pageNo,int pageSize) {
        cmdType = "appBusiness";
        methodName = "getAppByKeyword";
        requestParams = new RequestParam(key,pageNo,pageSize);
    }

    class RequestParam {
        String keyword;
        int pageNo;
        int pageSize;

        public RequestParam(String key,int pageNo,int pageSize) {
            keyword = key;
            this.pageNo = pageNo;
            this.pageSize = pageSize;
        }
    }
}
