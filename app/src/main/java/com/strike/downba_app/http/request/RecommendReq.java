package com.strike.downba_app.http.request;


import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/6/6.
 */
public class RecommendReq extends BaseRequest {

    RequestParam requestParams;

    public RecommendReq(String pageNo,String pageSize,String areaType){
        cmdType = "appService";
        methodName = "getRecommend";
        requestParams = new RequestParam(pageNo,pageSize,areaType);
    }
    class RequestParam{
        String pageNo;
        String pageSize;
        String type;

        public RequestParam(String pn,String ps,String at){
            pageNo = pn;
            pageSize = ps;
            type = at;
        }
    }
}
