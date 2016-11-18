package com.strike.downba_app.http.response;


import com.strike.downba_app.db.table.User;
import com.strike.downba_app.http.BaseResponse;

/**
 * Created by strike on 16/6/3.
 */
public class LoginRsp extends BaseResponse {
    public ResultData resultData = null;
    public class ResultData {
        public User user;
    }
}
