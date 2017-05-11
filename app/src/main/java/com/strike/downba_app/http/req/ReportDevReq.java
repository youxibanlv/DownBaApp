package com.strike.downba_app.http.req;

import com.strike.downba_app.http.BaseReq;
import com.strike.downba_app.http.bean.DevInfo;

/**
 * Created by strike on 2017/5/9.
 */

public class ReportDevReq extends BaseReq {

    public ReportDevReq(DevInfo devInfo) {
        cmdType = "userBusiness";
        methodName = "reportDev";
        requestParams = new RequestParam(devInfo);
    }

    public class RequestParam{
        public DevInfo devInfo;
        public RequestParam(DevInfo devInfo){
            this.devInfo = devInfo;
        }
    }
}
