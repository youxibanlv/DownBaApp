package com.strike.downba_app.http.request;

import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 2017/2/19.
 */

public class InfoDesReq extends BaseRequest {

    RequestParam requestParams;

    public InfoDesReq(int id) {
        cmdType = "appBusiness";
        methodName = "getInfoDes";
        requestParams = new RequestParam(id);
    }

    class RequestParam{
        int id;
        public RequestParam(int id){
            this.id = id;
        }
    }
}
