package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 16/6/6.
 */
public class GuessReq extends BaseReq {

    public GuessReq(int cateId){
        cmdType = "appBusiness";
        methodName = "getGuess";
        requestParams = new RequestParam(cateId);
    }
    class RequestParam{
        int cateId;

        public RequestParam(int cateId){

            this.cateId = cateId;
        }
    }
}
