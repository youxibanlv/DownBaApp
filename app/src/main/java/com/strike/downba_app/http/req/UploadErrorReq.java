package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 16/7/14.
 */
public class UploadErrorReq extends BaseReq {

    public UploadErrorReq(String filePath){
        cmdType = "fileBusiness";
        methodName = "uploadError";
        requestParams = new RequestParam(filePath);
    }
    class RequestParam{
        String filePath;
        public RequestParam(String filePath){
            this.filePath = filePath;
        }
    }
}
