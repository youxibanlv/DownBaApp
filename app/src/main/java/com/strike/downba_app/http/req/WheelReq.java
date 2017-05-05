package com.strike.downba_app.http.req;

import com.strike.downba_app.http.BaseReq;
import com.strike.downba_app.http.HttpConstance;

/**
 * Created by strike on 2017/5/2.
 */

public class WheelReq extends BaseReq {

    public WheelReq() {
        cmdType = HttpConstance.SEV_APP;
        methodName = HttpConstance.getWheel;
    }
}
