package com.strike.downba_app.http;


import com.strike.downba_app.base.AppContext;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downbaapp.R;

import org.xutils.common.Callback;

import java.util.concurrent.TimeoutException;

/**
 * Created by strike on 16/6/1.
 */
public abstract class NormalCallBack implements Callback.CommonCallback<String> {

    @Override
    public abstract void onSuccess(String result);


    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        if (ex instanceof TimeoutException){
            UiUtils.showTipToast(false, AppContext.getContext().getString(R.string.connect_timeout));
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public abstract void onFinished();
}
