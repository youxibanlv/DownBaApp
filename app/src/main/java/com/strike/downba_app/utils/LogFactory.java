package com.strike.downba_app.utils;

import android.util.Log;

import com.strike.downba_app.base.AppConfig;


/**
 * @author lbt
 * @ClassName: LogFactory
 * @Description: Log Factory
 * @date 2014-7-31
 */
public class LogFactory {

    private static boolean isDebug = AppConfig.DEBUG;

    public static void setIsDebug(boolean isTrue) {
        isDebug = isTrue;
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.d(tag, msg, tr);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            String tag = "=====";
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.e(tag, msg, tr);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.v(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.i(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.w(tag, msg, tr);
        }
    }
}
