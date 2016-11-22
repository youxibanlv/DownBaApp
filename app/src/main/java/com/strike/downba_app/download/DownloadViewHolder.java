package com.strike.downba_app.download;

import android.widget.TextView;

import org.xutils.x;

/**
 * Created by wyouflf on 15/11/10.
 */
public class DownloadViewHolder {

    protected DownloadInfo downloadInfo;

    private TextView textView;

    public DownloadViewHolder(TextView view, DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
        this.textView = view;
        x.view().inject(this, view);
    }

    public void refresh(String info) {
        if (textView != null)
            textView.setText(info);
    }

    public final DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    public void update(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }
    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
