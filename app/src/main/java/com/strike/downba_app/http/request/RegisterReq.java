package com.strike.downba_app.http.request;


import com.strike.downba_app.http.BaseRequest;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.SecurityUtils;

/**
 * Created by strike on 16/6/1.
 */
public class RegisterReq extends BaseRequest {

    RequestParam requestParams;

    public RegisterReq(String name,String password){
        cmdType = "userService";
        methodName = "register";
        requestParams = new RequestParam(name,password);
    }

    class RequestParam{
        String username;
        String password;
        public RequestParam(String name,String pass){
            username = name;
            password = SecurityUtils.encrypt128(pass, Constance.CLIENT_SECRET);
        }
    }
}
