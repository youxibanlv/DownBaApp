package com.strike.downba_app.http.response;


import com.strike.downba_app.db.table.App;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.entity.PageBean;

import java.util.List;

/**
 * Created by strike on 16/6/13.
 */
public class GetAppListRsp extends BaseResponse {
    public ResultData resultData = null;

    public List<App> getAppList() {
        return resultData.appList;
    }
    public PageBean getPageBean(){
        return resultData.pageBean;
    }

    class ResultData {
        List<App> appList = null;
        PageBean pageBean = null;
    }
}
