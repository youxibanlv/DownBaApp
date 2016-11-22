package com.strike.downba_app.download;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.strike.downba_app.base.AppContext;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.view.DownloadBtn;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by wyouflf on 15/11/10.
 */
/*package*/
class DownloadCallback implements Callback.CommonCallback<File>, Callback.ProgressCallback<File>,Callback.Cancelable {

    private DownloadInfo downloadInfo;
    private WeakReference<DownloadBtn> viewHolderRef;
    private DownloadManager downloadManager;
    private boolean cancelled = false;
    private Cancelable cancelable;

    public DownloadCallback(DownloadBtn viewHolder) {
        this.switchViewHolder(viewHolder);
    }

    public boolean switchViewHolder(DownloadBtn viewHolder) {
        if (viewHolder == null) return false;

        synchronized (DownloadCallback.class) {
            if (downloadInfo != null) {
                if (this.isStopped()) {
                    return false;
                }
            }
            this.downloadInfo = viewHolder.getDownloadInfo();
            this.viewHolderRef = new WeakReference<>(viewHolder);
        }
        return true;
    }

    public void setDownloadManager(DownloadManager downloadManager) {
        this.downloadManager = downloadManager;
    }

    public void setCancelable(Cancelable cancelable) {
        this.cancelable = cancelable;
    }

    public DownloadBtn getViewHolder() {
        if (viewHolderRef == null) return null;
        DownloadBtn viewHolder = viewHolderRef.get();
        if (viewHolder != null) {
            DownloadInfo downloadInfo = viewHolder.getDownloadInfo();
            if (this.downloadInfo != null && this.downloadInfo.equals(downloadInfo)) {
                return viewHolder;
            }
        }
        return null;
    }

    @Override
    public void onWaiting() {
        try {
            downloadInfo.setState(DownloadState.WAITING);
            downloadManager.updateDownloadInfo(downloadInfo);
        } catch (DbException ex) {
            LogUtil.e(ex.getMessage(), ex);
        }
        broadCasetDowanLoadState(downloadInfo,Constance.WAITTING);
    }

    @Override
    public void onStarted() {
        try {
            downloadInfo.setState(DownloadState.STARTED);
            downloadManager.updateDownloadInfo(downloadInfo);
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
            } catch (DbException ex) {
                LogUtil.e(ex.getMessage(), ex);
            }
            broadCasetDowanLoadState(downloadInfo,Constance.LOADING);
        }
    }

    @Override
    public void onSuccess(File result) {
        synchronized (DownloadCallback.class) {
            try {
                downloadInfo.setState(DownloadState.FINISHED);
                downloadManager.updateDownloadInfo(downloadInfo);
            } catch (DbException ex) {
                LogUtil.e(ex.getMessage(), ex);
            }
            broadCasetDowanLoadState(downloadInfo,Constance.COMPLETE);

        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        synchronized (DownloadCallback.class) {
            try {
                downloadInfo.setState(DownloadState.ERROR);
                downloadManager.updateDownloadInfo(downloadInfo);
            } catch (DbException e) {
                LogUtil.e(e.getMessage(), e);
            }
            broadCasetDowanLoadState(downloadInfo,Constance.FAILD);
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
        synchronized (DownloadCallback.class) {
            try {
                downloadInfo.setState(DownloadState.STOPPED);
                downloadManager.updateDownloadInfo(downloadInfo);
            } catch (DbException ex) {
                LogUtil.e(ex.getMessage(), ex);
            }
            broadCasetDowanLoadState(downloadInfo,Constance.PAUSE);
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
    private void broadCasetDowanLoadState(DownloadInfo downloadInfo, int state) {

        Intent arg0 = new Intent();
        arg0.setAction(Constance.ACTION_LOADING);
        arg0.putExtra(Constance.APP_ID, downloadInfo.getObjId());
        arg0.putExtra(Constance.CURRENT, downloadInfo.getProgress());
        arg0.putExtra(Constance.TOTAL, downloadInfo.getFileLength());
        arg0.putExtra(Constance.STATE, state);// 设置下载状态
        LocalBroadcastManager.getInstance(AppContext.getContext()).sendBroadcast(arg0);
    }
}
