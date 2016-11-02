package com.strike.downba_app.http.response;


import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.entity.Category;

import java.util.List;

/**
 * Created by strike on 16/7/2.
 */
public class GetCategoryRsp extends BaseResponse {
    public ResultData resultData = null;
    public List<Category> getResultList(){
        return resultData.list;
    }

    class ResultData {
        List<Category> list;
    }
}
