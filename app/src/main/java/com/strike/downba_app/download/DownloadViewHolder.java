package com.strike.downba_app.download;

import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.x;

import java.io.File;

/**
 * Created by wyouflf on 15/11/10.
 */
public abstract class DownloadViewHolder {

    protected DownloadInfo downloadInfo;
    private TextView textView;

    public DownloadViewHolder(TextView view, DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
        this.textView = view;
        x.view().inject(this, view);
    }

    public void refresh(String info){
        textView.setText(info);
    }
    public final DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    public void update(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }

    public abstract void onWaiting();

    public abstract void onStarted();

    public abstract void onLoading(long total, long current);

    public abstract void onSuccess(File result);

    public abstract void onError(Throwable ex, boolean isOnCallback);

    public abstract void onCancelled(Callback.CancelledException cex);
}
