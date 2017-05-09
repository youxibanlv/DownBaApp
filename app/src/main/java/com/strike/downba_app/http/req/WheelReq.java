package com.strike.downba_app.http.req;

import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 2017/5/2.
 */

public class WheelReq extends BaseReq {

    public WheelReq() {
        cmdType = "appBusiness";
        methodName = "getWheel";
    }
}
