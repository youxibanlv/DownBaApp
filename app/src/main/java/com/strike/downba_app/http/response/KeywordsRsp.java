package com.strike.downba_app.http.response;


import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.entity.Keyword;

import java.util.List;

/**
 * Created by strike on 16/6/12.
 */
public class KeywordsRsp extends BaseResponse {
    public ResultData resultData = null;
    public List<Keyword> getKeywords(){
        return resultData.keywords;
    }

    class ResultData {
        List<Keyword> keywords;
    }
}
