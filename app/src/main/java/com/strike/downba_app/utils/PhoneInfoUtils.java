package com.strike.downba_app.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.strike.downba_app.http.bean.DevInfo;

import org.xutils.common.util.MD5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


/**
 * 读取手机设备信息测试代码
 * 
 * @author strike
 */
public class PhoneInfoUtils {
	static TelephonyManager tm;


	/****** 获取手机设备信息 ********/
	public static DevInfo getDevInfo(Context ctx) {
		tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		DevInfo info = new DevInfo();
		try {
			info.setBrand(Build.BRAND);
			info.setModel(Build.MODEL);
			info.setVersionSdk(Build.VERSION.SDK);
			info.setVersionRelease(Build.VERSION.RELEASE);
			info.setMac(getMac(ctx));
			HashMap<String, String> map = getCpuString();
			if (map != null){
                info.setCpuInfo(map.toString());
			}
			info.setNetworkOperator(tm.getNetworkOperator()==null?"":tm.getNetworkOperator());
			info.setNetworkOperatorName(tm.getNetworkOperatorName()==null?"":tm.getNetworkOperatorName());
			info.setSubscriberId(tm.getSubscriberId()==null?"":tm.getSubscriberId());
			info.setImei(tm.getDeviceId()==null?"":tm.getDeviceId());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            info.setDevId(getDevId(ctx));
            return  info;
        }
	}

	public static String getDevId(Context ctx){
		StringBuilder deviceId = new StringBuilder();
		deviceId.append(System.currentTimeMillis()+"a");
		try {
			if (!TextUtils.isEmpty(getMac(ctx))){
				deviceId.append("mac");
				deviceId.append(getMac(ctx));
				return MD5.md5(deviceId.toString());
			}
			tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
			String imei = tm.getDeviceId();
			if (!TextUtils.isEmpty(imei)){
				deviceId.append("imei");
				deviceId.append(imei);
				return MD5.md5(deviceId.toString());
			}
			//序列号（sn）
			String sn = tm.getSimSerialNumber();
			if (!TextUtils.isEmpty(sn)){
				deviceId.append("sn");
				deviceId.append(sn);
				return MD5.md5(deviceId.toString());
			}
			//如果上面都没有,收集设备参数信息
			Field[] fields = Build.class.getDeclaredFields();
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					deviceId.append(field.getName());
					deviceId.append(field.get(null).toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return MD5.md5(deviceId.toString());
		}catch (Exception e){

		}
		return MD5.md5(deviceId.toString());
	}
	/***** 获取设备的mac地址 ******/
	public static String getMac(Context ctx) {
		String mac = "";
		WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if (wifiInfo.getMacAddress() != null) {
			mac = wifiInfo.getMacAddress();
		}
		return mac;
	}

	/***** 获取cpu信息 *****/
	public static String getCpuInfo() {
		// 这个类是一个很好用的工具，java中可以执行java命令，android中可以执行shell命令
		Runtime mRuntime = Runtime.getRuntime();
		try {
			// Process中封装了返回的结果和执行错误的结果
			Process mProcess = mRuntime.exec("/proc/cpuinfo");
			BufferedReader mReader = new BufferedReader(new InputStreamReader(
					mProcess.getInputStream()));
			StringBuffer mRespBuff = new StringBuffer();
			char[] buff = new char[1024];
			int ch = 0;
			while ((ch = mReader.read(buff)) != -1) {
				mRespBuff.append(buff, 0, ch);
			}
			mReader.close();
			return mRespBuff.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/****** 判断是否存在SD卡 *****/
	public static boolean ExistSDCard() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/***** 判断wifi是否可用 ****/
	public static boolean wifiAvaiable(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		return info != null && info.getType() == ConnectivityManager.TYPE_WIFI;
	}

	/***** 判断gps是否打开 *******/
	public static boolean ExistGPS(Context context) {
		LocationManager manager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		return manager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	/***** 判断apk是否第一次启动 ******/
	public static boolean APKisFirst(Context context, String packageName) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		boolean fist = sp.getBoolean(packageName, true);
		if (fist) {
			Editor edit = sp.edit();
			edit.putBoolean(packageName, false);
			edit.commit();
		}
		return fist;
	}

	/*
	 * cpu信息
	 */
	static public HashMap<String, String> getCpuString() {
		ArrayList<String> list = new ArrayList<>();
		HashMap<String, String> map = new HashMap<>();
		if (Build.CPU_ABI.equalsIgnoreCase("x86")) {
			return null;
		}
		try {
			RandomAccessFile reader = new RandomAccessFile("/proc/cpuinfo", "r");
			list = new ArrayList<>();
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				list.add(temp);
			}
			reader.close();
			for (int i = 0; i < list.size(); i++) {
				String[] strings = list.get(i).split(":");
				if (strings.length == 2) {
					map.put(strings[0].replaceAll("\t", ""),
							strings[1].replaceAll("\t", ""));
				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return map;
	}

	/***** 获取cpu核数 ********/
	public static int getNumCores() {
		// Private Class to display only CPU devices in the directory listing
		class CpuFilter implements FileFilter {
			@Override
			public boolean accept(File pathname) {
				// Check if filename is "cpu", followed by a single digit number
				return Pattern.matches("cpu[0-9]", pathname.getName());
			}
		}

		try {
			// Get directory containing CPU info
			File dir = new File("/sys/devices/system/cpu/");
			// Filter to only list the devices we care about
			File[] files = dir.listFiles(new CpuFilter());
			return files.length;
		} catch (Exception e) {
			e.printStackTrace();
			// Default to return 1 core
			return 1;
		}
	}

	/*
	 * cpu主频
	 */

	private final static String kCpuInfoMaxFreqFilePath = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";

	public static int getMaxCpuFreq() {
		int result = 0;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(kCpuInfoMaxFreqFilePath);
			br = new BufferedReader(fr);
			String text = br.readLine();
			result = Integer.parseInt(text.trim());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return result;
	}

	/*
	 * 正在运行的程序包名集合
	 */
	public static List<String> getRuningpkgName(Context context) {
		List<String> pkgNames = new ArrayList<String>();
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcessList = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcessList) {
			// 过滤系统应用和电话应用
			if ("system".equals(appProcess.processName)
					|| "com.Android.phone".equals(appProcess.processName)) {
				continue;
			}
			// int pid = appProcess.pid; // pid
			// String processName = appProcess.processName; // 进程名
			String[] pkgNameList = appProcess.pkgList; // 获得运行在该进程里的所有应用程序包
			// 输出所有应用程序的包名
			for (int i = 0; i < pkgNameList.length; i++) {
				String pkgName = pkgNameList[i];
				pkgNames.add(pkgName);
			}
		}
		return pkgNames;
	}

}
