package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 16/6/12.
 */
public class KeywordsReq extends BaseReq {

    public KeywordsReq(String key,int size){
        cmdType = "appBusiness";
        methodName = "getKeywords";
        requestParams = new RequestParam(key,size);
    }

    class RequestParam{
        String key;
        int size;
        public RequestParam(String keyword,int keySize){
            key = keyword;
            size = keySize;
        }
    }
}
