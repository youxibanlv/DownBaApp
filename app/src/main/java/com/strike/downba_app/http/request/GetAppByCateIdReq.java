package com.strike.downba_app.http.request;


import com.strike.downba_app.http.BaseRequest;

/**
 * Created by strike on 16/7/3.
 */
public class GetAppByCateIdReq extends BaseRequest {

    RequestParam requestParams;
    public GetAppByCateIdReq(int cate_id,int orderType,String pageNo,String pageSize){
        cmdType = "appService";
        methodName = "getListByCateId";
        requestParams = new RequestParam(cate_id,orderType,pageNo,pageSize);
    }
    class RequestParam{
        int cate_id;
        int orderType;
        String pageNo;
        String pageSize;

        public RequestParam(int cate_id,int orderType,String pageNo,String pageSize){
            this.cate_id = cate_id;
            this.orderType = orderType;
            this.pageNo = pageNo;
            this.pageSize = pageSize;
        }
    }
}
