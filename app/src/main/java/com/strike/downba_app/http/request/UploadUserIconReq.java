package com.strike.downba_app.http.request;


import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/7/14.
 */
public class UploadUserIconReq extends BaseRequest {

    RequestParam requestParams;

    public UploadUserIconReq (String filePath){
        cmdType = "upload";
        methodName = "uploadIcon";
        requestParams = new RequestParam(filePath);
    }
    class RequestParam{
        String filePath;
        public RequestParam(String filePath){
            this.filePath = filePath;
        }
    }
}
