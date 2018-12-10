package com.king.android.sharesdk.utils;

import android.util.Log;

import com.king.android.sharesdk.BuildConfig;

/**
 * @author lq.zeng
 * @date 2018/4/24
 */

public final class LogUtil {
    private static final boolean isDEBUG = BuildConfig.DEBUG;

    public static void Log_e(String tag, String msg) {
        if(isDEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void Log_d(String tag, String msg) {
        if(isDEBUG) {
            Log.d(tag, msg);
        }
    }
}
