package com.strike.downba_app.http.rsp;


import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.bean.PageBean;
import com.strike.downba_app.http.bean.Subject;

import java.util.List;

/**
 * Created by strike on 16/8/2.
 */
public class SubjectRsp extends BaseRsp {
    public ResultData resultData = null;

    public class ResultData {
        public List<Subject> subjects = null;
        public PageBean pageBean;
    }
}
