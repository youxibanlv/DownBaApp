package com.strike.downba_app.http.rsp;


import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.bean.Keyword;

import java.util.List;

/**
 * Created by strike on 16/6/12.
 */
public class KeywordsRsp extends BaseRsp {
    public ResultData resultData = null;
    public List<Keyword> getKeywords(){
        return resultData.keywords;
    }

    class ResultData {
        List<Keyword> keywords;
    }
}
