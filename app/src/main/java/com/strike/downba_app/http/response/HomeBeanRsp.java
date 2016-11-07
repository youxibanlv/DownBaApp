package com.strike.downba_app.http.response;

import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.entity.HomeBean;
import com.strike.downba_app.http.entity.PageBean;

import java.util.ArrayList;
import java.util.List;

public class HomeBeanRsp extends BaseResponse {

    public ResultData resultData = new ResultData();

    public class ResultData {
        public PageBean pageBean;
        public List<HomeBean> homeBeans = new ArrayList<>();
    }

}
