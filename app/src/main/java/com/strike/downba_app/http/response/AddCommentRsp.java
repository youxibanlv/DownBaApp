package com.strike.downba_app.http.response;


import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.entity.Comment;

import java.util.List;

public class AddCommentRsp extends BaseResponse {
	public ResultData resultData = new ResultData();

	public class ResultData {
		public List<Comment> list;
	}
}
