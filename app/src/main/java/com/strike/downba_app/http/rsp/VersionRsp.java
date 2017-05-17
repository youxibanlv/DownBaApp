package com.strike.downba_app.http.rsp;


import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.bean.Version;

public class VersionRsp extends BaseRsp {
	public ResultData resultData = new ResultData();

	public class ResultData {
		public Version version;
	}
}
