package google.architecture.common.upgrade;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import java.io.File;

import google.architecture.common.R;
import google.architecture.common.base.AppContext;
import google.architecture.common.base.ViewManager;
import google.architecture.common.dialog.CTAlertDialog;
import google.architecture.common.dialog.DialogsUtil;
import google.architecture.common.util.AppUtil;
import google.architecture.common.util.ToastUtils;
import google.architecture.coremodel.data.VersionInfo;
import google.architecture.coremodel.data.xlj.AppVersion;

/**
 * 自动更新 <br/>
 * 使用方法: 外部只需要调用update()方法，传入该应用已经分配好的key(key由应用在服务端注册时，由服务端生成给出)
 * 
 * @author: zlq
 * @date:2013-2-2
 */
public class UpdateManager {

	/* 界面更新处理的分类 */
	private static final int CREATE_DIALOG = 4;// 提示更新对话框
	private static final int SHOWTOASH = 5; // 其他提示
	private Activity mActivity;

	/* 请求服务端是否有更新时得到的版本信息 */
	private AppVersion mUpdateAPKItem;
	private boolean mShowNewest = false;

	/**
	 * 下载界面更新
	 */
	private Handler handler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case CREATE_DIALOG:
				String message = (String) msg.obj;
				showDownLoadDialog(mActivity, message);
				break;
			case SHOWTOASH:
				ToastUtils.showLongToast("应用已经是最新版");
				break;
			}
		}
	};
	private ProgressBar mProgressBar;
	private TextView mProgress;
	private CTAlertDialog dialog;
	private long mRequestId;
	private DownloadManager mDownloadManager;
	private DownloadObserver mDownloadObserver;
	private File mApkFile;

	/**
	 * @param activity
	 *            构造函数
	 */
	public UpdateManager(Activity activity, AppVersion info) {
		mUpdateAPKItem = info;
		this.mActivity = activity;
	}

	/**
	 * 开始检查更新
	 */
	public UpdateManager update() {
		Message message = handler.obtainMessage();
		message.obj = mUpdateAPKItem.getUpdateLog();
		message.what = CREATE_DIALOG;
		handler.sendMessage(message);
		return this;
	}

	/**
	 * 是否显示最新版本提示信息
	 * 
	 * @param show
	 */
	public void showNewestVersionToast(boolean show) {
		mShowNewest = show;
	}

	private void showDownLoadDialog(final Activity activity, String message) {
		final boolean isForceUpdate = Integer.valueOf(mUpdateAPKItem.getIsForce()) == 1;
		OnClickListener commotListener = v -> {
			if(!canDownloadState(activity)) { // 用户禁止了下载服务
				DialogsUtil.commTipDialog(activity, "您已禁用了下载服务\n请进入下载管理程序界面启用下载服务", v1 -> {
					if (isForceUpdate) {
						ViewManager.getInstance().finishAllActivity();
						android.os.Process.killProcess(android.os.Process.myPid());
					}
				}, v1 -> {
					String packageName = "com.android.providers.downloads";
					Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
					intent.setData(Uri.parse("package:" + packageName));
					activity.startActivity(intent);
				});
			} else {
				//intoDownloadManager(activity);
				initDownloadDialog(activity, isForceUpdate);
			}
		};

		OnClickListener cancelListener = v -> {
			// 判断是否属于需要强制更新的版本，如果是，则强制退出程序
			if (isForceUpdate) {
				ViewManager.getInstance().finishAllActivity();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		};

		DialogsUtil.showUpdateDialog(mActivity, message, isForceUpdate, commotListener, cancelListener);
	}

	private void intoDownloadManager(Activity activity) {
		if(!isDownloadManagerAvailable()) {
			return;
		}
		
		//确定下载的路径
		String apkName = mUpdateAPKItem.getVersionName() + ".apk";
		String dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        dirPath = dirPath.endsWith(File.separator) ? dirPath : dirPath + File.separator;
        String downloadApkPath = dirPath + apkName;
        LogUtils.tag("zlq").e("downloadApkPath = " + downloadApkPath);
        //要检查本地是否有安装包，有则删除重新下
        File apkFile = new File(downloadApkPath);
        if (apkFile.exists()) {
            boolean isDelSuc = apkFile.delete();
            LogUtils.tag("zlq").e("apk[" + apkName + "], isDelelte = " + isDelSuc);
        }
        
		DownloadManager dManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
		LogUtils.tag("zlq").e("download_url = " + mUpdateAPKItem.getDownloadUrl());
		Uri uri = Uri.parse(mUpdateAPKItem.getDownloadUrl().trim());
		DownloadManager.Request request = new DownloadManager.Request(uri);
		//支持移动网络和WIFI下载
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI); 
		request.setVisibleInDownloadsUi(true); // 设置为可见和可管理
		request.setAllowedOverRoaming(false); //移动网络不漫游
		request.setMimeType("application/vnd.android.package-archive");
		
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			request.allowScanningByMediaScanner(); // 设置为可被媒体扫描器找到
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //通知栏可见
		}
		
		// 设置下载路径和文件名
		// 可能无法创建Download文件夹，如无SDcard情况，系统会默认将路径设置为/data/data/com.android.providers.downloads/cache/xxx.apk
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, apkName);
		}
		
		long refernece = dManager.enqueue(request); // XXX 启动下载(此处发生过系统异常:用户禁用了下载服务)
		LogUtils.tag("zlq").e("refernece = " + refernece);
		// 把当前下载的ID保存起来
		SharedPreferences sPreferences = activity.getSharedPreferences(mActivity.getPackageName(), 0);
		sPreferences.edit().putLong(AppContext.VERSION, refernece).commit();
	}
	
	// 最小版本号大于9
    private static boolean isDownloadManagerAvailable() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }
	
	private boolean canDownloadState(Activity activity) {
        try {
            int state = activity.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


	private void initDownloadDialog(Activity activity,boolean isForceUpdate) {

		if(!isDownloadManagerAvailable()) {
			return;
		}

		//确定下载的路径
		String apkName = mUpdateAPKItem.getVersionName() + ".apk";
		String dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
		dirPath = dirPath.endsWith(File.separator) ? dirPath : dirPath + File.separator;
		String downloadApkPath = dirPath + apkName;
		LogUtils.tag("zlq").e("downloadApkPath = " + downloadApkPath);
		//要检查本地是否有安装包，有则删除重新下
		File apkFile = new File(downloadApkPath);
		if (apkFile.exists()) {
			boolean isDelSuc = apkFile.delete();
			LogUtils.tag("zlq").e("apk[" + apkName + "], isDelelte = " + isDelSuc);
		}

		mDownloadObserver = new DownloadObserver(new Handler());
		mDownloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
		activity.getContentResolver().registerContentObserver(Uri.parse("content://downloads/"), true, mDownloadObserver);
		LogUtils.tag("zlq").e("download_url = " + mUpdateAPKItem.getDownloadUrl());
		Uri uri = Uri.parse(mUpdateAPKItem.getDownloadUrl().trim());
		DownloadManager.Request request = new DownloadManager.Request(uri);
		//支持移动网络和WIFI下载
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
		request.setVisibleInDownloadsUi(true); // 设置为可见和可管理
		request.setAllowedOverRoaming(false); //移动网络不漫游
		request.setMimeType("application/vnd.android.package-archive");

		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			request.allowScanningByMediaScanner(); // 设置为可被媒体扫描器找到
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //通知栏可见
		}

		// 设置下载路径和文件名
		// 可能无法创建Download文件夹，如无SDcard情况，系统会默认将路径设置为/data/data/com.android.providers.downloads/cache/xxx.apk
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, apkName);
		}

		long refernece = mDownloadManager.enqueue(request); // XXX 启动下载(此处发生过系统异常:用户禁用了下载服务)
		mRequestId = refernece;
		LogUtils.tag("zlq").e("refernece = " + refernece);
		// 把当前下载的ID保存起来
		SharedPreferences sPreferences = activity.getSharedPreferences(mActivity.getPackageName(), 0);
		sPreferences.edit().putLong(AppContext.VERSION, refernece).commit();

		View downloadView = View.inflate(activity,R.layout.download_dialog,null);
		mProgressBar = (ProgressBar) downloadView.findViewById(R.id.progress);
		mProgress = (TextView) downloadView.findViewById(R.id.progressTV);
		dialog = new CTAlertDialog(activity);
		dialog.setCancelable(false);
		dialog.setView(downloadView);
		dialog.setBtnCancelTitle("取消", new CTAlertDialog.OnClickListener() {
			@Override
			public void onClick(View view, DialogInterface dialog) {

				mDownloadManager.remove(mRequestId);
				dialog.dismiss();
				if(isForceUpdate){
					ViewManager.getInstance().finishAllActivity();
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			}
		});
		dialog.setBtnConfirmVisibility(false);
		/*dialog.setBtnConfirmTitle("后台下载", new CTAlertDialog.OnClickListener() {
			@Override
			public void onClick(View view, DialogInterface dialog) {
				dialog.dismiss();
			}
		});*/
		dialog.show();

		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialogInterface) {
				if (mDownloadObserver != null) {
					mActivity.getContentResolver().unregisterContentObserver(mDownloadObserver);
				}
			}
		});
	}

	private class DownloadObserver extends ContentObserver {

		private DownloadObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange, Uri uri) {
			Log.i("downloadUpdate: ", "onChange(boolean selfChange, Uri uri)");
			queryDownloadStatus();
		}
	}

	private void queryDownloadStatus() {

		DownloadManager.Query query = new DownloadManager.Query().setFilterById(mRequestId);
		Cursor cursor = null;
		try {
			cursor = mDownloadManager.query(query);
			if (cursor != null && cursor.moveToFirst()) {
				//已经下载的字节数
				long currentBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
				//总需下载的字节数
				long totalBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
				//状态所在的列索引
				int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
				Log.i("downloadUpdate: ", currentBytes + " " + totalBytes + " " + status);

				mProgressBar.setProgress((int) (currentBytes * 100 / totalBytes));
				mProgress.setText((int) (currentBytes * 100 / totalBytes)+"%");

				if (DownloadManager.STATUS_SUCCESSFUL == status) {

					if (dialog != null && dialog.isShowing()){
						dialog.dismiss();
					}
//					AppUtil.Install(mActivity,mApkFile);
				}
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

}
