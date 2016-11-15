package com.strike.downba_app.http.request;

import com.strike.downba_app.http.BaseRequest;
import com.strike.downba_app.http.entity.Comment;

/**
 * Created by strike on 2016/11/14.
 */

public class AddCommentReq extends BaseRequest {

    public RequestParam requestParams ;
    public AddCommentReq(Comment comment){
        cmdType = "appService";
        methodName = "addComment";
        requestParams = new RequestParam(comment);
    }

    public class RequestParam{
        Comment comment;
        public RequestParam(Comment comment){
            this.comment = comment;
        }
    }
}
