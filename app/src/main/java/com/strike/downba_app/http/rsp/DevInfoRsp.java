package com.strike.downba_app.http.rsp;

import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.bean.DevInfo;

/**
 * Created by strike on 2017/5/9.
 */

public class DevInfoRsp extends BaseRsp {

    public ResultData resultData;

    public class ResultData{
        public DevInfo devInfo;

    }
}
