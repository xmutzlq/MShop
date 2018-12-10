package google.architecture.common.upgrade;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View.OnClickListener;

import com.apkfuns.logutils.LogUtils;

import java.io.File;

import google.architecture.common.base.AppContext;
import google.architecture.common.base.ViewManager;
import google.architecture.common.dialog.DialogsUtil;
import google.architecture.common.util.ToastUtils;
import google.architecture.coremodel.data.VersionInfo;

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
	private VersionInfo mUpdateAPKItem;
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

	/**
	 * @param activity
	 *            构造函数
	 */
	public UpdateManager(Activity activity, VersionInfo info) {
		mUpdateAPKItem = info;
		this.mActivity = activity;
	}

	/**
	 * 开始检查更新
	 */
	public UpdateManager update() {
		Message message = handler.obtainMessage();
		message.obj = mUpdateAPKItem.getUpdate_log();
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
		final boolean isForceUpdate = Integer.valueOf(mUpdateAPKItem.getIs_force()) == 1;
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
				intoDownloadManager(activity);
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
		String apkName = mUpdateAPKItem.getVersion_name() + ".apk";
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
		LogUtils.tag("zlq").e("download_url = " + mUpdateAPKItem.getDownload_url());
		Uri uri = Uri.parse(mUpdateAPKItem.getDownload_url().trim());
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
}
