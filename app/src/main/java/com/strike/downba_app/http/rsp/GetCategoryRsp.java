package com.strike.downba_app.http.rsp;


import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.bean.Cate;

import java.util.List;

/**
 * Created by strike on 16/7/2.
 */
public class GetCategoryRsp extends BaseRsp {
    public ResultData resultData = null;

    public class ResultData {
        public List<Cate> list;
    }
}
