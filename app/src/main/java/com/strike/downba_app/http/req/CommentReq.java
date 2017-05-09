package com.strike.downba_app.http.req;

import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 2017/5/8.
 */

public class CommentReq extends BaseReq {

    public CommentReq(int pageNo,int pageSize,int objType,int objId) {
        cmdType = "appBusiness";
        methodName = "getComments";
        requestParams = new RequestParam(pageNo,pageSize,objType,objId);
    }

    public class RequestParam{
        public int pageNo;
        public int pageSize;
        public int objType;
        public int objId;

        public RequestParam(int pageNo,int pageSize,int objType,int objId){
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            this.objType = objType;
            this.objId = objId;
        }
    }
}
