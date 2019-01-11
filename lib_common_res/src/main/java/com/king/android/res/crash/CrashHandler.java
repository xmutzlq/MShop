package com.king.android.res.crash;

import android.content.Context;

/**
 * @author lq.zeng
 * @date 2019/1/11
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";

    private static CrashHandler sInstance = new CrashHandler();

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private OnCrashListener mOnCrashListener;
    private CrashHandlerHelper mHandlerHelper;
    private boolean mIsInit = false;

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    /**
     * 初始化
     * @param context
     */
    public void init(Context context) {
        mIsInit = true;
        mHandlerHelper = new CrashHandlerHelper(context);
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (ex == null) {
            if (mDefaultHandler != null) {
                mDefaultHandler.uncaughtException(thread, ex);
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        } else {
            ex.printStackTrace();
            mHandlerHelper.saveCrashInfo2File(ex);
            if (mOnCrashListener != null) {
                mOnCrashListener.onCrash(ex);
            }
            if (mDefaultHandler != null) {
                mDefaultHandler.uncaughtException(thread, ex);
            }
        }

    }

    /**
     * 获取 crash 的文本信息，多个 crash 信息以数组分开
     * @return
     */
    public String[] getCrashLogs() {
        checkInit();
        return mHandlerHelper.getCrashLogs();
    }

    /**
     * 清空 crash 文件
     */
    public void clearCrashLogFile() {
        checkInit();
        mHandlerHelper.clearCrashLogFile();
    }

    /**
     * 获取 crash 文件的路径
     * @return
     */
    public String getCrashFilePath() {
        checkInit();
        return mHandlerHelper.mLogPathDir + mHandlerHelper.mCrashFileName;
    }

    public void setOnCrashListener(OnCrashListener listener) {
        mOnCrashListener = listener;
    }

    private void checkInit() {
        if (!mIsInit) {
            throw new UnsupportedOperationException("u should call init() before use CrashHandle's function...");
        }
    }
}
