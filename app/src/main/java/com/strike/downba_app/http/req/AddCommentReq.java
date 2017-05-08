package com.strike.downba_app.http.req;

import com.strike.downba_app.http.BaseReq;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.bean.Comment;

/**
 * Created by strike on 2016/11/14.
 */

public class AddCommentReq extends BaseReq {

    public AddCommentReq(Comment comment){
        cmdType = HttpConstance.SEV_APP;
        methodName = HttpConstance.addComment;
        requestParams = new RequestParam(comment);
    }

    public class RequestParam{
        public Comment comment;
        public RequestParam(Comment comment){
            this.comment = comment;
        }
    }
}
