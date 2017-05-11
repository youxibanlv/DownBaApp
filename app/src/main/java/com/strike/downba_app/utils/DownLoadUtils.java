package com.strike.downba_app.utils;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.strike.downba_app.download.DownloadInfo;
import com.strike.downba_app.download.DownloadManager;

import org.xutils.x;

import java.io.File;

/**
 * Created by strike on 16/6/17.
 */
public class DownLoadUtils {

    public static DownloadManager manager = DownloadManager.getInstance();

    public static long lastClick = 0;
    //安装应用
    public static void install(DownloadInfo info) {
        File file = new File(info.getFileSavePath());
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            x.app().startActivity(intent);
        }
    }

    //验证apk完整性
    public static boolean checkApk(DownloadInfo info) {
        PackageManager pm = x.app().getPackageManager();
        PackageInfo packageInfo = pm.getPackageArchiveInfo(info.getFileSavePath(), PackageManager.GET_ACTIVITIES);
        return packageInfo != null;
    }

    //删除apk
    public static void deleteApk(DownloadInfo info) {
        File file = new File(info.getFileSavePath());
        if (file.exists()) {
            File f = new File(file.getAbsolutePath() + System.currentTimeMillis());
            file.renameTo(f);
            f.delete();
        }
    }

//    public static void download(final AppInfo app) {
//        DownloadUrlReq req = new DownloadUrlReq(app.getApp_id(), app.getApp_version(),app.getUid());
//        req.sendRequest(new NormalCallBack() {
//            @Override
//            public void onSuccess(String result) {
//                if (!TextUtils.isEmpty(result)) {
//                    DownloadUrlRsp rsp = (DownloadUrlRsp) BaseResponse.getRsp(result, DownloadUrlRsp.class);
//                    if (rsp != null && rsp.result == HttpConstance.HTTP_SUCCESS) {
//                        String url = rsp.resultData;
//                        if (!TextUtils.isEmpty(url)) {
//                            try {
//                                manager.startDownload(url,app.getApp_id());
//                            } catch (DbException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//
//            }
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }
//   //初始化下载按钮
//    public static void initDownLoad(final App app, final TextView textView) {
//        DownloadInfo info = manager.getDownloadInfo(app.getApp_id());
//        //检查是否存在下载记录,初始化下载按钮显示文字
//        if (info != null) {
//            switch (info.getState()) {
//                case WAITING:
//                    textView.setText(R.string.queue_down);
//                    break;
//                case STARTED:
//                    textView.setText(info.getProgress() + "%");
//                    break;
//                case FINISHED:
//                    textView.setText(R.string.install);
//                    break;
//                case STOPPED:
//                    textView.setText(R.string.continue_down);
//                    break;
//                case ERROR:
//                    textView.setText(R.string.restart_down);
//                    break;
//            }
//        } else {
//            textView.setText(R.string.free_down);
//        }
//        //为按钮添加点击事件
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DownloadInfo downloadInfo = manager.getDownloadInfo(app.getApp_id());
//                if (System.currentTimeMillis() - lastClick < 2000) {
//                    UiUtils.showTipToast(false, "你点击的太快了，2秒一次哦！");
//                    return;
//                } else {
//                    lastClick = System.currentTimeMillis();
//                }
//                if (downloadInfo != null) {
//                    switch (downloadInfo.getState()) {
//                        case WAITING:
//                            try {
//                                manager.startDownload(downloadInfo.getUrl(),app.getApp_id());
//                            } catch (DbException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//                        case STARTED:
//                            textView.setText("继续下载");
//                            manager.stopDownload(downloadInfo);
//                            break;
//                        case FINISHED:
//                            if (checkApk(downloadInfo)) {
//                                install(downloadInfo);
//                            } else {
//                                deleteApk(downloadInfo);
//                                downloadInfo.setState(DownloadState.ERROR);
//                                try {
//                                    manager.removeDownload(downloadInfo);
//                                } catch (DbException e) {
//                                    e.printStackTrace();
//                                }
//                                UiUtils.showTipToast(false, "安装包出错，请重新下载");
//                            }
//                            break;
//                        case STOPPED:
//                            try {
//                                manager.startDownload(downloadInfo.getUrl(), app.getApp_id());
//                            } catch (DbException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//                        case ERROR:
//                            try {
//                                manager.startDownload(downloadInfo.getUrl(), app.getApp_id());
//                            } catch (DbException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//                    }
//                } else {
//                    download(app);
//                }
//            }
//        });
//    }
}
