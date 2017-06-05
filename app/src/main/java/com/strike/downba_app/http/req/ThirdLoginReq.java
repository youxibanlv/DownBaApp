package com.strike.downba_app.http.req;

import com.strike.downba_app.db.table.User;
import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 2017/6/1.
 */

public class ThirdLoginReq extends BaseReq {

    public ThirdLoginReq(User user) {
        cmdType = "userBusiness";
        methodName = "thirdLogin";
        requestParams = new RequestParam(user);
    }

    public  class RequestParam{
        public User user;
        public RequestParam(User user){
            this.user = user;
        }
    }
}
