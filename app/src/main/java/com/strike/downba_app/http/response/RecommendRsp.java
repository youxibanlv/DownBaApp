package com.strike.downba_app.http.response;


import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.entity.Recommend;

import java.util.List;

/**
 * Created by strike on 16/6/6.
 */
public class RecommendRsp extends BaseResponse {

    public ResultData resultData = null;

    public List<Recommend> getAppList() {
        return resultData.recommends;
    }

    class ResultData {
        List<Recommend> recommends = null;
    }
}
