package com.strike.downba_app.http.rsp;

import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.bean.Comment;

import java.util.List;

/**
 * Created by strike on 2017/5/8.
 */

public class CommentRsp extends BaseRsp {

    public ResultData resultData;

    public class ResultData{
        public List<Comment> comments;
        public int total;
    }
}
