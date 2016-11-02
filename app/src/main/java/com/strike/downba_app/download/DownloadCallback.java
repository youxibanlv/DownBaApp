package com.strike.downba_app.download;


import com.strike.downba_app.utils.DownLoadUtils;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.view.DownloadBtn;
import com.strike.downbaapp.R;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.x;

import java.io.EOFException;
import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by wyouflf on 15/11/10.
 */
/*package*/ class DownloadCallback implements
        Callback.CommonCallback<File>,
        Callback.ProgressCallback<File>,
        Callback.Cancelable {

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
            this.viewHolderRef = new WeakReference<DownloadBtn>(viewHolder);
        }
        return true;
    }

    public void setDownloadManager(DownloadManager downloadManager) {
        this.downloadManager = downloadManager;
    }

    public void setCancelable(Cancelable cancelable) {
        this.cancelable = cancelable;
    }

    private DownloadBtn getViewHolder() {
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
        downloadInfo.setState(DownloadState.WAITING);
        downloadManager.updateDownloadInfo(downloadInfo);
        DownloadBtn viewHolder = this.getViewHolder();
        if (viewHolder != null) {
            viewHolder.setText(R.string.queue_down);
        }
    }

    @Override
    public void onStarted() {
        downloadInfo.setState(DownloadState.STARTED);
        downloadManager.updateDownloadInfo(downloadInfo);
        DownloadBtn viewHolder = this.getViewHolder();
        if (viewHolder != null) {
            viewHolder.setText(0 + "%");
        }
    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {
        if (isDownloading) {
            downloadInfo.setState(DownloadState.STARTED);
            downloadInfo.setFileLength(total);
            downloadInfo.setProgress((int) (current * 100 / total));
            downloadManager.updateDownloadInfo(downloadInfo);
        }
        DownloadBtn viewHolder = this.getViewHolder();
        if (viewHolder != null) {
            viewHolder.setText(downloadInfo.getProgress() + "%");
        }
    }

    @Override
    public void onSuccess(File result) {
        synchronized (DownloadCallback.class) {
            downloadInfo.setState(DownloadState.FINISHED);
            downloadManager.updateDownloadInfo(downloadInfo);
            DownloadBtn viewHolder = this.getViewHolder();
            if (viewHolder != null) {
                DownLoadUtils.install(downloadInfo);
                viewHolder.setText(R.string.open);
            }
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        synchronized (DownloadCallback.class) {
            if (downloadInfo == null){
                switchViewHolder(viewHolderRef.get());
            }
            downloadInfo.setState(DownloadState.ERROR);
            downloadManager.updateDownloadInfo(downloadInfo);
            DownloadBtn viewHolder = this.getViewHolder();
            if (ex instanceof EOFException){
                DownLoadUtils.deleteApk(downloadInfo);
                downloadManager.removeDownload(downloadInfo);
                UiUtils.showTipToast(false,x.app().getString(R.string.server_busy));
            }
            if (ex instanceof HttpException){
                HttpException e = (HttpException) ex;
                if (e.getCode() == 404){
                    UiUtils.showTipToast(false, x.app().getString(R.string.server_not_found));
                }
            }
            if (viewHolder != null) {
                viewHolder.setText(R.string.restart_down);
            }
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
        synchronized (DownloadCallback.class) {
            downloadInfo.setState(DownloadState.STOPPED);
            downloadManager.updateDownloadInfo(downloadInfo);
            DownloadBtn viewHolder = this.getViewHolder();
            if (viewHolder != null) {
                viewHolder.setText(R.string.continue_down);
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
}
