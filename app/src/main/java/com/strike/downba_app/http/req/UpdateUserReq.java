package com.strike.downba_app.http.req;


import com.strike.downba_app.db.table.User;
import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 16/7/7.
 */
public class UpdateUserReq extends BaseReq {
    public UpdateUserReq(User user){
        cmdType = "userBusiness";
        methodName = "updateUser";
        requestParams = new RequestParam(user);
    }

    class RequestParam{
        User user;
        public RequestParam(User user){
            this.user = user;
        }
    }
}
