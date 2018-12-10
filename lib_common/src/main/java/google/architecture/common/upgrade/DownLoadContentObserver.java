package google.architecture.common.upgrade;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;

import com.apkfuns.logutils.LogUtils;

import java.lang.ref.WeakReference;

import google.architecture.common.base.AppContext;

/**
 * 下载进度监听
 * 
 * @author zlq
 * @date 2016年7月8日 下午1:54:18
 */
public class DownLoadContentObserver extends ContentObserver {
	public static final int HANDL_WHAT_DOWNLOAD = 110;
	public static final String CONTENT_URI = "content://downloads/my_downloads";
	private WeakReference<Context> context;
	private Handler handler;
	
	private SharedPreferences sPreferences;
	
	private long refernece = -1;
	private int progress;
	
	public DownLoadContentObserver(Context context, Handler handler) {
		super(handler);
		this.handler = handler;
		this.context = new WeakReference(context);
		sPreferences = context.getSharedPreferences(context.getPackageName(), 0);
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		queryDownLoadStatu(context.get());
		if (handler != null && progress > 0) {
			Message message = handler.obtainMessage();
			message.what = HANDL_WHAT_DOWNLOAD;
			message.arg1 = progress;
			handler.sendMessage(message);
		}
	}

	private void queryDownLoadStatu(Context context) {
		if(sPreferences != null && refernece == -1) {
			refernece = sPreferences.getLong(AppContext.VERSION, 0);
			LogUtils.tag("zlq").e("refernece## = " + refernece);
		}
		DownloadManager.Query query = new DownloadManager.Query();
		query.setFilterById(refernece);
		DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		final Cursor cursor = downloadManager.query(query);
		try {
			if (cursor != null && cursor.moveToFirst()) {
				final int totalColumn = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
				final int currentColumn = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
				int totalSize = cursor.getInt(totalColumn);
				int currentSize = cursor.getInt(currentColumn);
				float percent = (float) currentSize / (float) totalSize;
				progress = Math.round(percent * 100);
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
}
