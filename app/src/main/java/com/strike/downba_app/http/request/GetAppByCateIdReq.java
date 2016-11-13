package com.strike.downba_app.http.request;


import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/7/3.
 */
public class GetAppByCateIdReq extends BaseRequest {

    RequestParam requestParams;
    public GetAppByCateIdReq(int cate_id,int orderType,int pageNo,int pageSize){
        cmdType = "appService";
        methodName = "getAppListByCate";
        requestParams = new RequestParam(cate_id,orderType,pageNo,pageSize);
    }
    class RequestParam{
        int cate_id;
        int orderType;
        int pageNo;
        int pageSize;

        public RequestParam(int cate_id,int orderType,int pageNo,int pageSize){
            this.cate_id = cate_id;
            this.orderType = orderType;
            this.pageNo = pageNo;
            this.pageSize = pageSize;
        }
    }
}
