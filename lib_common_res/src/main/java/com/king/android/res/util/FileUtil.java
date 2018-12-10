package com.king.android.res.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * 文件操作工具类
 * 
 * @author zlq
 */
public final class FileUtil {

	/**
	 * SDCARD的根目录
	 *
	 * @return
	 */
	public static File getExternalStorageDirectory() {
		return Environment.getExternalStorageDirectory();
	}

	/**
	 * 判断是否有SDCARD
	 * 
	 * @return
	 */
	public static boolean isSDcardExist() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 检测文件路径的目录是否存在，不存在就尝试创建目录
	 * 
	 * @param path
	 *            文件路径
	 * @return 目录是否可用
	 */
	public static boolean checkPathDir(String path) {
		if (TextUtils.isEmpty(path) || path.charAt(path.length() - 1) == '/') {
			return false;
		}
		String dirStr = path;
		int end = dirStr.lastIndexOf('/') + 1;
		if (end != -1) {
			dirStr = dirStr.substring(0, end);
		} else {
			return false;
		}
		return checkDir(dirStr);
	}

	/**
	 * 检测目录是否存在，不存在就尝试创建目录
	 * 
	 * @param path
	 *            文件目录
	 * @return 目录是否可用
	 */
	public static boolean checkDir(String dirPath) {
		if (TextUtils.isEmpty(dirPath)) {
			return false;
		}
		File dir = new File(dirPath);
		if (dir.exists() || dir.mkdirs()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断某个文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isFileExists(String filePath) {
		if (!isSDcardExist()) {
			return false;
		}
		File file = new File(filePath);
		if (file != null && file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 获取存储卡的可用空间
	 * 
	 * @return
	 */
	public static long getStorageSize() {
		if (isSDcardExist()) {
			StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
			long blockSize = stat.getBlockSize();
			return stat.getAvailableBlocks() * blockSize;
		}
		return 0;
	}

	private static boolean updateFileTime(String dir, String fileName) {
		File file = new File(dir, fileName);
		if (!file.exists()) {
			return false;
		}
		long newModifiedTime = System.currentTimeMillis();
		return file.setLastModified(newModifiedTime);
	}

	/**
	 * 创建文件目录
	 * 
	 * @param path
	 */
	public static boolean createFileDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
			return true;
		}
		file = null;
		return false;
	}

	/**
	 * 写文件到SDCARD指定的位置
	 * 
	 * @param str
	 * @param filePath
	 * @return
	 */
	public static boolean outPutToFile(String str, String filePath) {
		if (!isSDcardExist()) {
			return false;
		}
		if (TextUtils.isEmpty(str)) {
			return false;
		}
		if (TextUtils.isEmpty(filePath)) {
			return false;
		}
		try {
			FileWriter fw = new FileWriter(filePath, true);
			fw.write(str);
			fw.flush();
			fw.close();
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean outCrashToFile(String str, String filePath, int maxSize) {
		if (!isSDcardExist()) {
			return false;
		}
		if (TextUtils.isEmpty(str)) {
			return false;
		}
		if (TextUtils.isEmpty(filePath)) {
			return false;
		}
		try {
			File file = new File(filePath);
			if (file.exists()) {
				if (maxSize < file.length()) {
					file.delete();
				}
			}
			FileWriter fw = new FileWriter(filePath, true);
			fw.write(str);
			fw.flush();
			fw.close();
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public static void copyFileToSdcard(Context context, String fileName, String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return;
		}
		try {
			AssetManager am = context.getAssets();
			InputStream is = am.open(fileName);
			BufferedInputStream bis = new BufferedInputStream(is);
			if (!file.exists()) {
				file.createNewFile();
			}
			if (file.isFile()) {
				RandomAccessFile oSavedFile = new RandomAccessFile(file, "rw");
				oSavedFile.seek(0);
				int bufferSize = 4096;
				byte[] b = new byte[bufferSize];
				int nRead;
				int currentBytes = 0;
				int bytesNotified = currentBytes;
				long timeLastNotification = 0;
				for (;;) {
					nRead = bis.read(b, 0, bufferSize);

					if (nRead == -1) {
						break;
					}
					currentBytes += nRead;
					oSavedFile.write(b, 0, nRead);
					long now = System.currentTimeMillis();
					if (currentBytes - bytesNotified > bufferSize && now - timeLastNotification > 1500) {
						bytesNotified = currentBytes;
						timeLastNotification = now;
					}
				}
				oSavedFile.close();
			}
			is.close();
		} catch (Exception e) {

		}
	}

	public static boolean RenameFileName(String oldFileName, String newFileName) {
		if (TextUtils.isEmpty(oldFileName) || TextUtils.isEmpty(newFileName)) {
			return false;
		}
		File file = new File(oldFileName);
		if (!file.exists()) {
			return false;
		}
		File newFile = new File(newFileName);
		if (newFile.exists()) {
			newFile.delete();
		}
		if (!file.renameTo(newFile)) {
			return false;
		} else {
			file.delete();
		}
		return true;
	}

	public static boolean DeleteFile(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return true;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			return true;
		}
		if (file.delete()) {
			return true;
		}
		return false;
	}

	public static void deleteAllFiles(File root) {
		File files[] = root.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) { // 判断是否为文件夹
					deleteAllFiles(f);
					try {
						f.delete();
					} catch (Exception e) {
					}
				} else {
					if (f.exists()) { // 判断是否存在
						deleteAllFiles(f);
						try {
							f.delete();
						} catch (Exception e) {
						}
					}
				}
			}
		}
	}

	public static void copyFileToFile(Context context, String fileName, InputStream is) {
		try {
			// AssetManager am = context.getAssets();
			// InputStream is = am.open(fileName);
			BufferedInputStream bis = new BufferedInputStream(is);
			FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_WORLD_WRITEABLE);

			int bufferSize = 4096;
			byte[] b = new byte[bufferSize];
			int nRead;
			int currentBytes = 0;
			int bytesNotified = currentBytes;
			long timeLastNotification = 0;
			for (;;) {
				nRead = bis.read(b, 0, bufferSize);

				if (nRead == -1) {
					break;
				}
				currentBytes += nRead;
				fos.write(b, 0, nRead);
				long now = System.currentTimeMillis();
				if (currentBytes - bytesNotified > bufferSize && now - timeLastNotification > 1500) {
					bytesNotified = currentBytes;
					timeLastNotification = now;
				}
			}
			fos.flush();
			fos.close();
			is.close();
		} catch (Exception e) {

		}
	}

	public static Intent openFile(String filePath) {

		if (TextUtils.isEmpty(filePath)) {
			return null;
		}
		File file = new File(filePath);
		if (!file.exists())
			return null;
		/* 取得扩展名 */
		String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length())
				.toLowerCase();
		/* 依扩展名的类型决定MimeType */
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg")
				|| end.equals("wav")) {
			return getAudioFileIntent(filePath);
		} else if (end.equals("3gp") || end.equals("mp4")) {
			return getAudioFileIntent(filePath);
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg")
				|| end.equals("bmp")) {
			return getImageFileIntent(filePath);
		} else if (end.equals("apk")) {
			return getApkFileIntent(filePath);
		} else if (end.equals("ppt")) {
			return getPptFileIntent(filePath);
		} else if (end.equals("xls")) {
			return getExcelFileIntent(filePath);
		} else if (end.equals("doc")) {
			return getWordFileIntent(filePath);
		} else if (end.equals("pdf")) {
			return getPdfFileIntent(filePath);
		} else if (end.equals("chm")) {
			return getChmFileIntent(filePath);
		} else if (end.equals("txt")) {
			return getTextFileIntent(filePath, false);
		} else {
			return getAllIntent(filePath);
		}
	}

	// Android获取一个用于打开APK文件的intent
	public static Intent getAllIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "*/*");
		return intent;
	}

	// Android获取一个用于打开APK文件的intent
	public static Intent getApkFileIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		return intent;
	}

	// Android获取一个用于打开VIDEO文件的intent
	public static Intent getVideoFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	// Android获取一个用于打开AUDIO文件的intent
	public static Intent getAudioFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	// Android获取一个用于打开Html文件的intent
	public static Intent getHtmlFileIntent(String param) {

		Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content")
				.encodedPath(param).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	// Android获取一个用于打开图片文件的intent
	public static Intent getImageFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	// Android获取一个用于打开PPT文件的intent
	public static Intent getPptFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	// Android获取一个用于打开Excel文件的intent
	public static Intent getExcelFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	// Android获取一个用于打开Word文件的intent
	public static Intent getWordFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	// Android获取一个用于打开CHM文件的intent
	public static Intent getChmFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/x-chm");
		return intent;
	}

	// Android获取一个用于打开文本文件的intent
	public static Intent getTextFileIntent(String param, boolean paramBoolean) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (paramBoolean) {
			Uri uri1 = Uri.parse(param);
			intent.setDataAndType(uri1, "text/plain");
		} else {
			Uri uri2 = Uri.fromFile(new File(param));
			intent.setDataAndType(uri2, "text/plain");
		}
		return intent;
	}

	// Android获取一个用于打开PDF文件的intent
	public static Intent getPdfFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}
}
