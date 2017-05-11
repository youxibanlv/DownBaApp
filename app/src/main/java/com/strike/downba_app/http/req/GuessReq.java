package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/6/6.
 */
public class GuessReq extends BaseRequest {

    RequestParam requestParams;

    public GuessReq(String recommend_type){
        cmdType = "appService";
        methodName = "getGuess";
        requestParams = new RequestParam(recommend_type);
    }
    class RequestParam{
        String recommend_type;

        public RequestParam(String recommend_type){

            this.recommend_type = recommend_type;
        }
    }
}
