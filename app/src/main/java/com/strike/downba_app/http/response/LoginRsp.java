package com.strike.downba_app.http.response;


import com.strike.downba_app.http.BaseResponse;

/**
 * Created by strike on 16/6/3.
 */
public class LoginRsp extends BaseResponse {
    public ResultData resultData = null;
    class ResultData {
        int uid;
        String username;
        String password;
        String token;
        String phone;
        String nickname;
        String icon;
        String alipay;
        int point;
    }
}
