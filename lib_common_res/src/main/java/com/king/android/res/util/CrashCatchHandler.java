package com.king.android.res.util;

import android.text.TextUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 捕捉程序异常保存到指定的文件
 * 
 * @author zlq
 */
public class CrashCatchHandler implements UncaughtExceptionHandler {

	private static final String CRASH_REPORTER_EXTENSION = "crash-file.txt";
	private static final int MAX_SIZE = 512 * 1024;
	private UncaughtExceptionHandler mUncaughtExceptionHandler;
	private String outPutDir;

	public CrashCatchHandler(String outPutDir) {
		this.outPutDir = outPutDir;
		mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		saveCrashInfoToFile(ex);
		if (mUncaughtExceptionHandler != null) {
			mUncaughtExceptionHandler.uncaughtException(thread, ex);
		}
		android.os.Process.killProcess(android.os.Process.myPid()); // 去除dialog
	}

	/**
	 * 保存异常信息到文件 6.0读写权限申请在主界面onCreate,若是在此之前崩溃则无法写入存储中
	 * 
	 * @param ex
	 * @return
	 */
	private String saveCrashInfoToFile(Throwable ex) {
		Writer info = new StringWriter();
		PrintWriter printWriter = new PrintWriter(info);
		ex.printStackTrace(printWriter);

		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}

		String result = info.toString();
		printWriter.close();

		StringBuilder sb = new StringBuilder();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
		Date date = new Date();
		String timestamp = sdf.format(date);

		sb.append(timestamp);
		sb.append("\n");
		sb.append(result);
		sb.append("\n\n\n");

		String filePath = getFilePath();
		if (FileUtil.outCrashToFile(sb.toString(), filePath, MAX_SIZE)) {
			return filePath;
		}
		return null;
	}

	private String getFilePath() {
		if (TextUtils.isEmpty(outPutDir)) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(outPutDir);
		sb.append(CRASH_REPORTER_EXTENSION);
		return sb.toString();
	}
}
