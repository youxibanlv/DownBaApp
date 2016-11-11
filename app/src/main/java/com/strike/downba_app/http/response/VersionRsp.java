package com.strike.downba_app.http.response;


import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.entity.Version;

public class VersionRsp extends BaseResponse {
	public ResultData resultData = new ResultData();

	public class ResultData {
		public Version version;
	}
}
