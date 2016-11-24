package com.strike.downba_app.download;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.UiUtils;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;

/**
 * Created by wyouflf on 15/11/10.
 */
/*package*/
class DownloadCallback implements Callback.CommonCallback<File>, Callback.ProgressCallback<File>, Callback.Cancelable {

    private DownloadInfo downloadInfo;
    private Integer position;
    private DownloadManager downloadManager;
    private boolean cancelled = false;
    private Cancelable cancelable;

    public DownloadCallback(DownloadInfo info,Integer position) {
        this.downloadInfo = info;
        this.position = position;
    }

    public boolean switchViewHolder(Integer position) {
        if (position == null) return false;

        synchronized (DownloadCallback.class) {
            if (downloadInfo != null) {
                if (this.isStopped()) {
                    return false;
                }
            }
            this.position = position;
        }
        return true;
    }

    public void setDownloadManager(DownloadManager downloadManager) {
        this.downloadManager = downloadManager;
    }

    public void setCancelable(Cancelable cancelable) {
        this.cancelable = cancelable;
    }


    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public void onWaiting() {
        try {
            downloadInfo.setState(DownloadState.WAITING);
            downloadManager.updateDownloadInfo(downloadInfo);
            broadcastDownload(Constance.WAITTING);
        } catch (DbException ex) {
            LogUtil.e(ex.getMessage(), ex);
        }
    }

    @Override
    public void onStarted() {
        try {
            downloadInfo.setState(DownloadState.STARTED);
            downloadManager.updateDownloadInfo(downloadInfo);
            broadcastDownload(Constance.LOADING);
        } catch (DbException ex) {
            LogUtil.e(ex.getMessage(), ex);
        }
    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {
        if (isDownloading) {
            try {
                downloadInfo.setState(DownloadState.STARTED);
                downloadInfo.setFileLength(total);
                downloadInfo.setProgress((int) (current * 100 / total));
                downloadManager.updateDownloadInfo(downloadInfo);
                broadcastDownload(Constance.LOADING);
            } catch (DbException ex) {
                LogUtil.e(ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void onSuccess(File result) {
        synchronized (DownloadCallback.class) {
            try {
                downloadInfo.setState(DownloadState.FINISHED);
                downloadManager.updateDownloadInfo(downloadInfo);
                broadcastDownload(Constance.COMPLETE);
            } catch (DbException ex) {
                LogUtil.e(ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        synchronized (DownloadCallback.class) {
            try {
                downloadInfo.setState(DownloadState.ERROR);
                downloadManager.updateDownloadInfo(downloadInfo);
                broadcastDownload(Constance.PAUSE);
            } catch (DbException e) {
                LogUtil.e(e.getMessage(), e);
            }
            UiUtils.showTipToast(false,ex.getMessage());
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
        synchronized (DownloadCallback.class) {
            try {
                downloadInfo.setState(DownloadState.STOPPED);
                downloadManager.updateDownloadInfo(downloadInfo);
                broadcastDownload(Constance.PAUSE);
            } catch (DbException ex) {
                LogUtil.e(ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void onFinished() {
        cancelled = false;
    }

    private boolean isStopped() {
        DownloadState state = downloadInfo.getState();
        return isCancelled() || state.value() > DownloadState.STARTED.value();
    }

    @Override
    public void cancel() {
        cancelled = true;
        if (cancelable != null) {
            cancelable.cancel();
        }
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }


    // 广播下载状态
    private void broadcastDownload(int state) {
            Intent arg0 = new Intent();
            arg0.setAction(Constance.ACTION_DOWNLOAD);
            arg0.putExtra(Constance.APP_ID, downloadInfo.getObjId());
            arg0.putExtra(Constance.PROGRESS, downloadInfo.getProgress());
            arg0.putExtra(Constance.STATE, state);// 设置下载状态
            LocalBroadcastManager.getInstance(x.app()).sendBroadcast(arg0);
        LogUtil.e("------objId="+downloadInfo.getObjId()+",state = "+state +" ,progress ="+ downloadInfo.getProgress());
        }
}
