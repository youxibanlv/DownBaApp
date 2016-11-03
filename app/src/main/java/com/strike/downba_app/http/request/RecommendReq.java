package com.strike.downba_app.http.request;


import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/6/6.
 */
public class RecommendReq extends BaseRequest {

    RequestParam requestParams;

    public RecommendReq(String recommend_type){
        cmdType = "appService";
        methodName = "getRecommend";
        requestParams = new RequestParam(recommend_type);
    }
    class RequestParam{
        String recommend_type;

        public RequestParam(String recommend_type){

            this.recommend_type = recommend_type;
        }
    }
}
