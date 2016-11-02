package com.strike.downba_app.base;

import android.content.Context;

/**
 * Created by strike on 16/5/31.
 */
public class AppContext {

    private static Context context;

    public static void setContext(Context mContext){
        context = mContext;
    }
    public static Context getContext(){
        return context;
    }
}
