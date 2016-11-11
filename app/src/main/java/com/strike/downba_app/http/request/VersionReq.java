package com.strike.downba_app.http.request;


import com.strike.downba_app.http.BaseRequest;
import com.strike.downba_app.http.entity.Version;

public class VersionReq extends BaseRequest {

	public RequestParam requestParams;

	public VersionReq(Version version) {
		cmdType = "versionService";
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
