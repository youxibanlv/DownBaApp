package com.strike.downba_app.http.rsp;


import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.bean.Subject;

/**
 * Created by strike on 16/8/2.
 */
public class SbDetailsRsp extends BaseRsp {
    public ResultData resultData = null;

    public class ResultData {
        public Subject subject;
    }
}
