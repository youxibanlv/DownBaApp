package com.strike.downba_app.http.rsp;

import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.bean.AppHome;

/**
 * Created by strike on 2017/5/4.
 */

public class AppHomeRsp extends BaseRsp{

    public ResultData resultData = null;

    public class ResultData{
        public AppHome appHome;
    }
}
