package com.strike.downba_app.http.rsp;


import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.bean.Comment;

import java.util.List;

public class AddCommentRsp extends BaseRsp {
	public ResultData resultData = new ResultData();

	public class ResultData {
		public List<Comment> list;
	}
}
