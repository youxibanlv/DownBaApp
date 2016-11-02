package com.strike.downba_app.http.request;


import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/8/4.
 */
public class InfoReq extends BaseRequest {
    RequestParam requestParams;

    public InfoReq(int pageNo,int pageSize, int cate_id) {
        cmdType = "appService";
        methodName = "getInfoList";
        requestParams = new RequestParam(pageNo,pageSize,cate_id);
    }
    class RequestParam{
        int pageNo;
        int pageSize;
        int cate_id;
        public RequestParam(int pageNo,int pageSize, int cate_id){
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            this.cate_id = cate_id;
        }
    }
}
