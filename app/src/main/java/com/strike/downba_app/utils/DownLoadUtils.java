package com.strike.downba_app.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.strike.downba_app.db.table.App;
import com.strike.downba_app.download.DownloadInfo;
import com.strike.downba_app.download.DownloadManager;
import com.strike.downba_app.download.DownloadState;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.request.DownloadUrlReq;
import com.strike.downba_app.http.response.DownloadUrlRsp;
import com.strike.downba_app.view.DownloadBtn;
import com.strike.downbaapp.R;

import org.xutils.x;

import java.io.File;

/**
 * Created by strike on 16/6/17.
 */
public class DownLoadUtils {

    private long lastClick = 0;
    private Context context;
    private DownloadManager manager;

    public DownLoadUtils(Context context) {
        this.context = context;
        manager = DownloadManager.getInstance();
    }

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
        if (packageInfo != null) {
            return true;
        } else {
            return false;
        }
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

    public void download(final App app, final DownloadBtn downloadBtn) {
        DownloadUrlReq req = new DownloadUrlReq(app.getApp_id(), app.getApp_version(),app.getUid());
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    DownloadUrlRsp rsp = (DownloadUrlRsp) BaseResponse.getRsp(result, DownloadUrlRsp.class);
                    if (rsp != null && rsp.result == HttpConstance.HTTP_SUCCESS) {
                        String url = rsp.resultData;
                        if (!TextUtils.isEmpty(url)) {
                            manager.startDownload(url, app, downloadBtn);
                        }
                    }
                }

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void initDownLoad(final App app, final DownloadBtn textView) {
        DownloadInfo info = manager.getDownloadInfo(app);
        //检查是否存在下载记录,初始化下载按钮显示文字
        if (info != null) {
            switch (info.getState()) {
                case WAITING:
                    manager.startDownload(info.getUrl(),app,textView);
                    textView.setText(R.string.queue_down);
                    break;
                case STARTED:
                    manager.startDownload(info.getUrl(),app,textView);
                    textView.setText(info.getProgress() + "%");
                    break;
                case FINISHED:
                    textView.setText(R.string.install);
                    break;
                case STOPPED:
                    textView.setText(R.string.continue_down);
                    break;
                case ERROR:
                    textView.setText(R.string.restart_down);
                    break;
            }
        } else {
            textView.setText(R.string.free_down);
        }
        //为按钮添加点击事件
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadInfo downloadInfo = manager.getDownloadInfo(app);
                if (System.currentTimeMillis() - lastClick < 2000) {
                    UiUtils.showTipToast(false, context.getString(R.string.click_too_fast));
                    return;
                } else {
                    lastClick = System.currentTimeMillis();
                }
                if (downloadInfo != null) {
                    switch (downloadInfo.getState()) {
                        case WAITING:
                            manager.startDownload(downloadInfo.getUrl(),app,textView);
                            break;
                        case STARTED:
                            textView.setText(R.string.continue_down);
                            manager.stopDownload(downloadInfo,textView);
                            break;
                        case FINISHED:
                            if (checkApk(downloadInfo)) {
                                install(downloadInfo);
                            } else {
                                deleteApk(downloadInfo);
                                downloadInfo.setState(DownloadState.ERROR);
                                manager.removeDownload(downloadInfo);
                                UiUtils.showTipToast(false, context.getString(R.string.retry_download));
                                textView.setText(R.string.restart_down);
                            }
                            break;
                        case STOPPED:
                            textView.setText(downloadInfo.getProgress() + "%");
                            manager.startDownload(downloadInfo.getUrl(), app, textView);
                            break;
                        case ERROR:
                            manager.startDownload(downloadInfo.getUrl(), app, textView);
                            break;
                    }
                } else {
                    download(app, textView);
                }
            }
        });
    }
}
