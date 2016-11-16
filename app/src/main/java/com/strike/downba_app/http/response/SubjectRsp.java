package com.strike.downba_app.http.response;


import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.entity.PageBean;
import com.strike.downba_app.http.entity.Subject;

import java.util.List;

/**
 * Created by strike on 16/8/2.
 */
public class SubjectRsp extends BaseResponse {
    public ResultData resultData = null;

    public class ResultData {
        public List<Subject> subjects = null;
        public PageBean pageBean;
    }
}
