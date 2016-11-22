package com.strike.downba_app.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.strike.downba_app.base.AppConfig;
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

    private String fileSavePath;

    public DownLoadUtils(Context context) {
        this.context = context;
        manager = DownloadManager.getInstance();
        fileSavePath = AppConfig.DOWNLOAD_PATH;
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
        DownloadUrlReq req = new DownloadUrlReq(app.getApp_id(), app.getApp_version(), app.getUid());
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    DownloadUrlRsp rsp = (DownloadUrlRsp) BaseResponse.getRsp(result, DownloadUrlRsp.class);
                    if (rsp != null && rsp.result == HttpConstance.HTTP_SUCCESS) {
                        String url = rsp.resultData;
                        if (!TextUtils.isEmpty(url)) {
                            manager.startDownload(url, NumberUtil.parseToInt(app.getApp_id()), fileSavePath + app.getApp_title() + ".apk", downloadBtn);
                        }
                    }
                }

            }

            @Override
            public void onFinished() {

            }
        });
    }
    //初始化下载按钮
    public void initDownLoad(final App app, final DownloadBtn holder) {
        DownloadInfo info = manager.getInfoByObjId(NumberUtil.parseToInt(app.getApp_id()));
        //检查是否存在下载记录,初始化下载按钮显示文字
        if (info != null) {
            switch (info.getState()) {
                case WAITING:
                    holder.refresh(info.getObjId(),"队列中。。。");
                    break;
                case STARTED:
                    holder.refresh(info.getObjId(),info.getProgress() + "%");
                    break;
                case FINISHED:
                    holder.refresh(info.getObjId(),"打 开");
                    break;
                case STOPPED:
                    holder.refresh(info.getObjId(),"继续下载");
                    break;
                case ERROR:
                    holder.refresh(info.getObjId(),"重新开始");
                    break;
                default:
                    holder.refresh(info.getObjId(),"安 装");
                    break;
            }
        }else{
            holder.refresh(NumberUtil.parseToInt(app.getApp_id()),"安 装");
        }
        //为按钮添加点击事件
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadInfo downloadInfo = manager.getInfoByObjId(NumberUtil.parseToInt(app.getApp_id()));
                if (System.currentTimeMillis() - lastClick < 2000) {
                    UiUtils.showTipToast(false, context.getString(R.string.click_too_fast));
                    return;
                } else {
                    lastClick = System.currentTimeMillis();
                }
                if (downloadInfo != null) {
                    switch (downloadInfo.getState()) {
                        case WAITING:
                            manager.startDownload(downloadInfo.getUrl(), downloadInfo.getObjId(), downloadInfo.getFileSavePath(), holder);
                            break;
                        case STARTED:
                            manager.stopDownload(downloadInfo);
                            break;
                        case FINISHED:
                            if (checkApk(downloadInfo)) {
                                install(downloadInfo);
                            } else {
                                deleteApk(downloadInfo);
                                downloadInfo.setState(DownloadState.ERROR);
                                manager.removeDownload(downloadInfo);
                                UiUtils.showTipToast(false, context.getString(R.string.retry_download));
                                holder.refresh(downloadInfo.getObjId(),"重新下载");
                            }
                            break;
                        case STOPPED:
                            manager.startDownload(downloadInfo.getUrl(), downloadInfo.getObjId(), downloadInfo.getFileSavePath(), holder);
                            break;
                        case ERROR:
                            manager.startDownload(downloadInfo.getUrl(), downloadInfo.getObjId(), downloadInfo.getFileSavePath(), holder);
                            break;
                    }
                } else {
                    download(app, holder);
                }
            }
        });

    }

//    class MyBroadcastReceiver extends BroadcastReceiver{
//
//        private WeakReference<DownloadBtn> viewHolderRef;
//        private DownloadInfo info;
//
//        public MyBroadcastReceiver(DownloadBtn holder) {
//            viewHolderRef = new WeakReference<>(holder);
//            info = holder.getDownloadInfo();
//        }
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (viewHolderRef == null || info == null){
//                return;
//            }
//            DownloadBtn viewHolder = viewHolderRef.get();
//            if (viewHolder == null){
//                return;
//            }
//            int objId = intent.getIntExtra(Constance.APP_ID, -1);
//            if (objId != -1 && objId == info.getObjId()) {
//                int state = intent.getIntExtra(Constance.STATE, -1);
//                if (state != -1) {
//                    switch (state) {
//                        case Constance.WAITTING:
//                            viewHolder.refresh(objId,"队列中。。");
//                            break;
//                        case Constance.LOADING:
//                            viewHolder.refresh(objId,info.getProgress() + "%");
//                            break;
//                        case Constance.COMPLETE:
//                            //安装apk
//                            if (checkApk(info)) {
//                                install(info);
//                                viewHolder.refresh(objId,"打 开");
//                            } else {
//                                deleteApk(info);
//                                info.setState(DownloadState.ERROR);
//                                viewHolder.refresh(objId,"重新下载");
//                            }
//                            break;
//                        case Constance.FAILD:
//                            viewHolder.refresh(objId,"重新下载");
//                            break;
//                        case Constance.PAUSE:
//                            viewHolder.refresh(objId,"继续下载");
//                            break;
//                    }
//                }
//            }
//        }
//    }
//
//    //注册广播，更新下载进度
//    public void registReciver(DownloadBtn holder) {
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Constance.ACTION_LOADING);
//        MyBroadcastReceiver receiver = new MyBroadcastReceiver(holder);
//        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
//        localBroadcastManager.registerReceiver(receiver, intentFilter);
//        map.put(holder, receiver);
//    }
//
//    public void unRigistReciver(MyBroadcastReceiver receiver,DownloadBtn btn){
//        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
//        manager.unregisterReceiver(receiver);
//        map.remove(btn);
//    }
}
