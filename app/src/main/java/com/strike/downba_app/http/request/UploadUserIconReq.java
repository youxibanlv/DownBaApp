package com.strike.downba_app.http.request;


import com.strike.downba_app.db.table.User;
import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/7/14.
 */
public class UploadUserIconReq extends BaseRequest {

    RequestParam requestParams;

    public UploadUserIconReq (String filePath,User user){
        cmdType = "userService";
        methodName = "uploadIcon";
        requestParams = new RequestParam(filePath,user);
    }
    class RequestParam{
        String filePath;
        User user;
        public RequestParam(String filePath,User user){
            this.filePath = filePath;
            this.user = user;
        }
    }
}
