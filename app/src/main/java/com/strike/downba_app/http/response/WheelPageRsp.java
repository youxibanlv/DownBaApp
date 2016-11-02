package com.strike.downba_app.http.response;


import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.entity.WheelPage;

import java.util.List;

/**
 * Created by strike on 16/6/8.
 */
public class WheelPageRsp extends BaseResponse {

    public ResultData resultData = null;

    public List<WheelPage> getWheelPages(){
        return  resultData.wheelPages;
    }
    class ResultData {
        List<WheelPage> wheelPages;
    }
}
