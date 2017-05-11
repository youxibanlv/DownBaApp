package com.strike.downba_app.http.bean;
//用户手机信息实体对象
public class DevInfo {
	private int id;
	private String devId;//设备id
	private String imei;// imei 354286054659328 串号
	private String networkOperator;// 46000 网络运营商
	private String networkOperatorName;// CMCC 网络运营商名字
	private String subscriberId;// imsi 460008315040827 国际网络...串
	private String brand; // samsung 品牌
	private String model; // GT-I9268 手机型号
	private String versionRelease; // 4.1.2 系统版本
	private String versionSdk; // 16 sdk版本
	private String mac; // mac地址
	private String cpuInfo;// cpu信息
	private int status;//用户状态，0正常使用，1暂停使用，2已冻结

	public DevInfo() {
	}
	
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getNetworkOperator() {
		return networkOperator;
	}

	public void setNetworkOperator(String networkOperator) {
		this.networkOperator = networkOperator;
	}

	public String getNetworkOperatorName() {
		return networkOperatorName;
	}

	public void setNetworkOperatorName(String networkOperatorName) {
		this.networkOperatorName = networkOperatorName;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVersionRelease() {
		return versionRelease;
	}

	public void setVersionRelease(String versionRelease) {
		this.versionRelease = versionRelease;
	}

	public String getVersionSdk() {
		return versionSdk;
	}

	public void setVersionSdk(String versionSdk) {
		this.versionSdk = versionSdk;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public void setCpuInfo(String cpuInfo) {
		this.cpuInfo = cpuInfo;
	}

	public String getCpuInfo() {
		return cpuInfo;
	}

	@Override
	public String toString() {
		return "DevInfo [imei=" + imei + ", networkOperator="
				+ networkOperator + ", networkOperatorName="
				+ networkOperatorName + ", subscriberId=" + subscriberId
				+ ", brand=" + brand + ", model=" + model + ", versionRelease="
				+ versionRelease + ", versionSdk=" + versionSdk + ", mac="
				+ mac + ", cpuInfo=" + cpuInfo + "]";
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
