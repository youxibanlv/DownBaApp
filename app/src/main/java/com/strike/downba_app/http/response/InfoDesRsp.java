package com.strike.downba_app.http.response;

import com.strike.downba_app.http.BaseResponse;

/**
 * Created by strike on 2017/2/19.
 */

public class InfoDesRsp extends BaseResponse {

    public ResultData resultData;

    public class ResultData {
        public String infoBody = "";
    }
}
