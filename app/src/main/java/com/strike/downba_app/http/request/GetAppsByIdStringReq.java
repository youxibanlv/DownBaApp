package com.strike.downba_app.http.request;


import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/8/3.
 */
public class GetAppsByIdStringReq extends BaseRequest {

    RequestParam requestParams;

    public GetAppsByIdStringReq(String idList){
        cmdType = "appBusiness";
        methodName = "getAppsByIdString";
        requestParams = new RequestParam(idList);
    }

    class RequestParam{
        String ids;
        public RequestParam(String idList){
            this.ids = idList;
        }
    }
}
