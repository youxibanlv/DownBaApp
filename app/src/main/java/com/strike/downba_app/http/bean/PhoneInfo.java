package com.strike.downba_app.http.bean;
//用户手机信息实体对象
public class PhoneInfo {
	private String deviceId;// imei 354286054659328 串号
	private String networkOperator;// 46000 网络运营商
	private String networkOperatorName;// CMCC 网络运营商名字
	private String subscriberId;// imsi 460008315040827 国际网络...串
	private String brand; // samsung 品牌
	private String model; // GT-I9268 手机型号
	private String versionRelease; // 4.1.2 系统版本
	private String versionSdk; // 16 sdk版本
	private String mac; // mac地址
	private String cpuInfo;// cpu信息
	private Integer uid;//用户id
	private String ipaddress;//ip地址
	
	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	private String channel;//渠道号
	

	// private String networkCountryIso;// cn
	// private String simOperator;// 46000
	// private String simSerialNumber;// 89860050317892100827
	// private String mobile;
	// private String board; // piranha
	// private String bootloader; // I9268ZMBMC2
	// private String cpu_abi; // armeabi-v7a
	// private String device; // superiorcmcc
	// private String fingerprint; //
	// samsung/superiorzm/superiorcmcc:4.1.2/JZO54K/I9268ZMBMC2:user/release-keys
	// private String hardware; // superior
	// private String host; // SEP-120
	// private String id; // JZO54K
	// private String manufacturer; // samsung
	// private String product; // superiorzm
	// private String radio; // I9268ZMBMC2
	// private String serial; // cff037c96094f30
	// private String type; // user
	// private String user; // se.infra
	// private String versionCodename; // REL
	// private String versionIncremental; // I9268ZMBMC2
	// private String versionSdk_int; // 16
	// private String ip;

	public PhoneInfo() {
	}
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
		return "PhoneInfo [deviceId=" + deviceId + ", networkOperator="
				+ networkOperator + ", networkOperatorName="
				+ networkOperatorName + ", subscriberId=" + subscriberId
				+ ", brand=" + brand + ", model=" + model + ", versionRelease="
				+ versionRelease + ", versionSdk=" + versionSdk + ", mac="
				+ mac + ", cpuInfo=" + cpuInfo + "]";
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

}
