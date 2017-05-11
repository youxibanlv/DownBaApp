package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseReq;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.SecurityUtils;

/**
 * Created by strike on 16/6/3.
 */
public class LoginReq extends BaseReq {

    public LoginReq(String name,String password){
        cmdType = "userBusiness";
        methodName = "login";
        requestParams = new RequestParam(name,password);
    }

   public  class RequestParam{
       public String username;
       public String password;
        public RequestParam(String name,String pass){
            username = name;
            password = SecurityUtils.encrypt128(pass, Constance.CLIENT_SECRET);
        }
    }
}
