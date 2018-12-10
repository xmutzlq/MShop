package com.king.android.res.application;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.king.android.res.util.CrashCatchHandler;

import google.architecture.coremodel.util.Utils;
import solid.ren.skinlibrary.base.SkinBaseApplication;

/**
 * @author lq.zeng
 * @date 2018/4/19
 */

public abstract class BaseApp extends SkinBaseApplication {

    private static BaseApp sInstance;
    private static Handler mHandler;

    private TelephonyManager mTelephonyManager;

    public static BaseApp getIns() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Utils.init(this);
        //7.0安装应用闪退问题
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
        // 处理程序崩溃日志记录
        catchError();
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActivityManager activityMgr = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        activityMgr.restartPackage(getPackageName());
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    public TelephonyManager getTelephonyManager() {
        return mTelephonyManager;
    }

    /**
     * 异常扑捉的处理，记录日志
     */
    private void catchError() {
        String fileDir = getAppSdcardDir();
        if (!TextUtils.isEmpty(fileDir)) {
            new CrashCatchHandler(fileDir);
        }
    }

    /**
     * 设置程序在SDCARD的目录
     *
     * @return
     */
    protected abstract String getAppSdcardDir();

    /**
     * 设置是否日志写入SDCARD
     *
     * @return
     */
    protected abstract boolean isWriteLogToSdcard();

    /**
     * 设置是否DEBUG模式
     *
     * @return
     */
    protected abstract boolean isDebug();

}
