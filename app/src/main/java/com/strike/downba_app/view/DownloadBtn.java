package com.strike.downba_app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.strike.downba_app.download.DownloadInfo;
import com.strike.downbaapp.R;


/**
 * Created by strike on 16/6/20.
 */
public class DownloadBtn extends TextView {
    protected DownloadInfo downloadInfo;

    public DownloadBtn(Context context) {
        super(context);
        init();
    }

    public DownloadBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int)(getResources().getDimension(R.dimen.wdp120)),
                (int)(getResources().getDimension(R.dimen.wdp50)));
        params.gravity = Gravity.CENTER_HORIZONTAL;
        this.setLayoutParams(params);
        this.setBackgroundResource(R.mipmap.bg_install);
        this.setGravity(Gravity.CENTER);
        this.setTextColor(getResources().getColor(R.color.green));
    }

    public final DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    public void update(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }
}
