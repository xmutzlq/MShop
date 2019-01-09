package google.architecture.common.socket.utils;


import android.util.Log;

import google.architecture.common.BuildConfig;

public class DeHongSocketLog {

    public static boolean debug = BuildConfig.DEBUG;
    public static final String TAG = "DeHongSocket";

    public static void debug(boolean debug1) {
        debug = debug1;
    }

    public static void d(String tag, Object o) {
        if (debug) {
            Log.d(TAG, tag + " " + o.toString());
        }
    }

    public static void e(String tag, Object o) {
        if (debug) {
            Log.e(TAG, tag + " " + o.toString());
        }
    }
}
