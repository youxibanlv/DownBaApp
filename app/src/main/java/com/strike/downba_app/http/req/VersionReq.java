package com.strike.downba_app.http.req;


import com.strike.downba_app.http.BaseReq;
import com.strike.downba_app.http.bean.Version;

public class VersionReq extends BaseReq {

	public VersionReq(Version version) {
		cmdType = "versionBusiness";
		methodName = "checkUpdate";
		requestParams = new RequestParam(version);
	}

	public class RequestParam {
		public Version version;
		public RequestParam(Version version) {
			this.version = version;
		}
	}
}
