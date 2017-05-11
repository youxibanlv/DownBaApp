package com.strike.downba_app.http.rsp;


import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.bean.Info;
import com.strike.downba_app.http.bean.PageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 16/8/4.
 */
public class InfoRsp extends BaseRsp {
    public ResultData resultData = new ResultData();

    public class ResultData {
        public   List<Info> infoList = new ArrayList<>();
        public PageBean pageBean = new PageBean();
    }
}
