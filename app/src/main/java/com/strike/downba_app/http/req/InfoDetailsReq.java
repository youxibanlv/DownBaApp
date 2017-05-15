package com.strike.downba_app.http.req;

import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 2017/2/19.
 */

public class InfoDetailsReq extends BaseReq {


    public InfoDetailsReq(int infoId) {
        cmdType = "appBusiness";
        methodName = "getInfoDetails";
        requestParams = new RequestParam(infoId);
    }

    class RequestParam {
        int infoId;
        public RequestParam(int infoId) {
            this.infoId = infoId;
        }
    }
}
