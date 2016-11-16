package com.strike.downba_app.http.response;


import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.entity.Info;
import com.strike.downba_app.http.entity.PageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 16/8/4.
 */
public class InfoRsp extends BaseResponse {
    public ResultData resultData = new ResultData();

    public class ResultData {
        public   List<Info> infoList = new ArrayList<>();
        public PageBean pageBean = new PageBean();
    }
}
