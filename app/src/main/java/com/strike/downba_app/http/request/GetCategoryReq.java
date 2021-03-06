package com.strike.downba_app.http.request;


import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/7/2.
 */
public class GetCategoryReq extends BaseRequest {

    RequestParam requestParams;

    public GetCategoryReq(int cateId){
        cmdType = "appService";
        methodName = "getCateByParentId";
        requestParams = new RequestParam(cateId);
    }

    class RequestParam{
        int parentId;
        public RequestParam(int parentId){
            this.parentId = parentId;
        }
    }
}
