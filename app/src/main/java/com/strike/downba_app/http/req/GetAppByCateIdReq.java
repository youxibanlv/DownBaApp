package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseReq;

/**
 * Created by strike on 16/7/3.
 */
public class GetAppByCateIdReq extends BaseReq {

    public GetAppByCateIdReq(int cate_id,int orderType,int pageNo,int pageSize){
        cmdType = "appBusiness";
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
