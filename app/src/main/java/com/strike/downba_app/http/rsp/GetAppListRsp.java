package com.strike.downba_app.http.rsp;


import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.bean.AppInfo;
import com.strike.downba_app.http.bean.PageBean;

import java.util.List;

/**
 * Created by strike on 16/6/13.
 */
public class GetAppListRsp extends BaseRsp {
    public ResultData resultData = null;

    public class ResultData {
       public List<AppInfo> appList = null;
       public PageBean pageBean = null;
    }
}
