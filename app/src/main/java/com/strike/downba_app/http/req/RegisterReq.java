package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseReq;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.SecurityUtils;

/**
 * Created by strike on 16/6/1.
 */
public class RegisterReq extends BaseReq {

    public RegisterReq(String name,String password){
        cmdType = "userBusiness";
        methodName = "register";
        requestParams = new RequestParam(name,password);
    }

    public class RequestParam{
        public String username;
        public String password;
        public RequestParam(String name,String pass){
            username = name;
            password = SecurityUtils.encrypt128(pass, Constance.CLIENT_SECRET);
        }
    }
}
