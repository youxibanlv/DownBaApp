package com.strike.downba_app.http.rsp;


import com.strike.downba_app.db.table.User;
import com.strike.downba_app.http.BaseRsp;

/**
 * Created by strike on 16/6/3.
 */
public class UserInfoRsp extends BaseRsp {
    public ResultData resultData = new ResultData();

    public class ResultData {
        public User user;
    }
}
