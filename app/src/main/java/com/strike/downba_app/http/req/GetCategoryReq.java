package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 16/7/2.
 */
public class GetCategoryReq extends BaseReq {

    public GetCategoryReq(int cateId){
        cmdType = "appBusiness";
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
