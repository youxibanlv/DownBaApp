package com.strike.downba_app.http.req;

import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 2017/5/10.
 */

public class UserInfoReq extends BaseReq {

    public UserInfoReq(String token) {
        cmdType = "userBusiness";
        methodName = "checkUser";
        requestParams = new RequestParam(token);
    }

    public class RequestParam{
        public String token;
        public RequestParam(String token){
            this.token = token;
        }
    }
}
