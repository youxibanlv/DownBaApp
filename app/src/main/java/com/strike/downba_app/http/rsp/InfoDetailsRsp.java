package com.strike.downba_app.http.rsp;

import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.bean.Info;

/**
 * Created by strike on 2017/2/19.
 */

public class InfoDetailsRsp extends BaseRsp {

    public ResultData resultData;

    public class ResultData {
        public Info info;
    }
}
