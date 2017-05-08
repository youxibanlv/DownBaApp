package com.strike.downba_app.http.rsp;


import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.bean.AppInfo;

/**
 * Created by strike on 16/6/29.
 */
public class AppDetailsRsp extends BaseRsp {
    public ResultData resultData = null;

    public class ResultData {
        public AppInfo appInfo = null;
    }
}
