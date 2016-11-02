package com.strike.downba_app.http.request;


import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/7/2.
 */
public class GetCategoryReq extends BaseRequest {

    RequestParam requestParams;

    public GetCategoryReq(int cate_id){
        cmdType = "appService";
        methodName = "getCategory";
        requestParams = new RequestParam(cate_id);
    }

    class RequestParam{
        int cate_id;
        public RequestParam(int cate_id){
            this.cate_id = cate_id;
        }
    }
}
