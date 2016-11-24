package com.strike.downba_app.download;

import com.strike.downba_app.utils.DownLoadUtils;
import com.strike.downba_app.utils.UiUtils;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;

import java.io.File;

/**
 * Created by wyouflf on 15/11/10.
 */
/*package*/
class DownloadCallback implements Callback.CommonCallback<File>, Callback.ProgressCallback<File>, Callback.Cancelable {

    private DownloadInfo downloadInfo;
    private DownloadManager downloadManager;
    private DataChanger changer;
    private boolean cancelled = false;
    private Cancelable cancelable;

    public DownloadCallback(DownloadInfo info) {
        this.downloadInfo = info;
        changer = DataChanger.getInstance();
    }

    public void setDownloadManager(DownloadManager downloadManager) {
        this.downloadManager = downloadManager;
    }

    public boolean switchViewHolder(DownloadInfo info){
        if (downloadInfo == null) return false;
        if (!downloadInfo.getObjId().equals(info.getObjId())) {
            try {
                downloadManager.removeDownload(downloadInfo);
            } catch (DbException e) {
                e.printStackTrace();
            }
            return false;
        }
        synchronized (DownloadCallback.class) {
            if (this.isStopped()) {
                return false;
            }
            this.downloadInfo = info;
        }
        return true;
    }
    public void setCancelable(Cancelable cancelable) {
        this.cancelable = cancelable;
    }

    @Override
    public void onWaiting() {
        try {
            downloadInfo.setState(DownloadState.WAITING);
            downloadManager.updateDownloadInfo(downloadInfo);
            changer.notifyObservers(downloadInfo);
        } catch (DbException ex) {
            LogUtil.e(ex.getMessage(), ex);
        }
    }

    @Override
    public void onStarted() {
        try {
            downloadInfo.setState(DownloadState.STARTED);
            downloadManager.updateDownloadInfo(downloadInfo);
            changer.notifyDownloadDataChange(downloadInfo);
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
                changer.notifyDownloadDataChange(downloadInfo);
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
                if ( DownLoadUtils.checkApk(downloadInfo)){
                    DownLoadUtils.install(downloadInfo);
                }else{
                    DownLoadUtils.deleteApk(downloadInfo);
                    downloadInfo.setState(DownloadState.WAITING);
                    downloadManager.updateDownloadInfo(downloadInfo);
                }
                changer.notifyDownloadDataChange(downloadInfo);
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
                changer.notifyDownloadDataChange(downloadInfo);
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
                changer.notifyDownloadDataChange(downloadInfo);
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
}
