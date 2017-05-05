package com.strike.downba_app.utils;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.strike.downba_app.base.AppConfig;
import com.strike.downba_app.view.dialog.CustomDialogClickListener;
import com.strike.downba_app.view.dialog.YesNoDialog;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.entity.Version;
import com.strike.downba_app.http.request.VersionReq;
import com.strike.downba_app.http.response.VersionRsp;
import com.strike.downbaapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/***
 * 版本更新的工具类
 * 
 * ****/
public class UpdateManager {
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	private Context mContext;
	/* 更新进度条 */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;

	private Version version;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	/********* 获取服务器版本号 *************/
	public void checkUpdate(final boolean notify) {
		final Version localVersion = getLocalVersion();
		VersionReq req = new VersionReq(localVersion);
		req.sendRequest(new NormalCallBack() {
			@Override
			public void onSuccess(String result) {
				VersionRsp rsp = (VersionRsp) BaseResponse.getRsp(result,VersionRsp.class);
				final Version serverVersion = rsp.resultData.version;
				if (serverVersion!=null){
					if (serverVersion.getVersion_code() > localVersion.getVersion_code()){
                        version = serverVersion;
						//提示更新
						if (notify){
							new YesNoDialog(mContext, "检测到新版本", version.getVersion_info(), "取消", "更新", false,null, new CustomDialogClickListener() {
								@Override
								public void click(Dialog dialog) {
                                    dialog.dismiss();
                                    showDownloadDialog();
								}
							}).show();
						}else {
							//强制更新

						}
					}
				}
			}

			@Override
			public void onFinished() {

			}
		});
	}

	/**
	 * 获取软件版本号 和渠道号
	 */
	public Version getLocalVersion() {
		Version version = new Version();
		try {
			PackageManager pm = mContext.getPackageManager();
            String pkgName = mContext.getPackageName();
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			version.setVersion_code(pm.getPackageInfo(pkgName, 0).versionCode);
			version.setVersion_name(pm.getPackageInfo(pkgName,0).versionName);
			version.setChannel_id(pm.getApplicationInfo(pkgName,PackageManager.GET_META_DATA).metaData.getInt("CHANNEL"));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 显示软件下载对话框
	 */
	private void showDownloadDialog() {
		// 构造软件下载对话框
		Builder builder = new Builder(mContext);
		builder.setTitle("正在更新");
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		// 取消更新
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置取消状态
				cancelUpdate = true;
			}
		});
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// 现在文件
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk() {
		// 启动新线程下载软件
		new downloadApkThread().start();
    }

	/**
	 * 下载文件线程
	 * 
	 * @author coolszy
	 * @date 2012-4-26
	 * @blog http://blog.92coding.com
	 */
	private class downloadApkThread extends Thread {
		@Override
		public void run() {
			try {
//				 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 获得存储卡的路径
					mSavePath = AppConfig.getBasePath()+"/apk";
                    String apkUrl = version.getUrl();
					URL url = new URL(apkUrl);
                    String fileName = apkUrl.substring(apkUrl.lastIndexOf("/")+1,apkUrl.length());
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdirs();
					}
					File apkFile = new File(mSavePath, fileName);
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 取消下载对话框显示
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk() {
        String fileName = version.getUrl().substring(version.getUrl().lastIndexOf("/")+1,version.getUrl().length());
		File apkfile = new File(mSavePath, fileName);
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}


}
