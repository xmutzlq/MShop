package google.architecture.common.upgrade;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.apkfuns.logutils.LogUtils;

import java.lang.ref.WeakReference;

import google.architecture.common.R;
import google.architecture.common.dialog.DialogsUtil;
import google.architecture.common.widget.NumberProgressBar;
import google.architecture.common.widget.OnProgressBarListener;
import google.architecture.coremodel.data.VersionInfo;

public class DownLoadProcess implements DownLoadAPI, OnProgressBarListener {

	private WeakReference<Activity> mActivity;
	private Dialog mDownLoadProgressDialog;
	private NumberProgressBar mDownloadProgressBar;

	private UpdateManager mUpdateManager;
	private DownLoadContentObserver mDownLoadContentObserver;

	private int preProgress;
	private boolean isDialogShown;
	private boolean isUpdating;

	private Handler mHandler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case DownLoadContentObserver.HANDL_WHAT_DOWNLOAD:
				if (mDownLoadProgressDialog != null && !mDownLoadProgressDialog.isShowing() && !isDialogShown) {
					isDialogShown = true;
					mDownLoadProgressDialog.show();
				}
				int progress = msg.arg1 - preProgress;
				if (mDownloadProgressBar != null && mDownloadProgressBar.isShown()) {
					mDownloadProgressBar.incrementProgressBy(progress);
				}
				preProgress = msg.arg1;
				break;
			}
		};
	};

	public DownLoadProcess(Activity activity, VersionInfo versionInfo) {
		if (activity == null || activity.isFinishing()) return;
		mActivity = new WeakReference(activity);
		mUpdateManager = new UpdateManager(mActivity.get(), versionInfo);
		mDownLoadContentObserver = new DownLoadContentObserver(mActivity.get(), mHandler);
	}

	@Override
	public void registDownLoadContentObserver() {
		mActivity.get().getContentResolver().registerContentObserver(Uri.parse(DownLoadContentObserver.CONTENT_URI),
				true, mDownLoadContentObserver);// 注册ContentObserver
	}

	@Override
	public void unregistDownLoadContentObserver() {
		mActivity.get().getContentResolver().unregisterContentObserver(mDownLoadContentObserver);
	}

	@Override
	public void init() {
		mDownLoadProgressDialog = DialogsUtil.buildDownloadStatusDialog(mActivity.get());
		mDownloadProgressBar = mDownLoadProgressDialog.findViewById(R.id.down_load_progress);
		mDownloadProgressBar.setOnProgressBarListener(this);
		mDownLoadProgressDialog.setCancelable(false);
		mDownLoadProgressDialog.setCanceledOnTouchOutside(false);
	}

	@Override
	public void startDownLoad() {
		if (!isUpdating) {
			isUpdating = true;
			if (mUpdateManager != null) {
				mUpdateManager.update();
			}
		}
	}

	@Override
	public void overDownLoad() {
		if (mDownLoadProgressDialog != null) {
			mDownLoadProgressDialog.dismiss();
			mDownLoadProgressDialog = null;
		}
		mHandler.removeMessages(DownLoadContentObserver.HANDL_WHAT_DOWNLOAD);
		mHandler = null;
	}

	@Override
	public void onProgressChange(int current, int max) {
		LogUtils.tag("zlq").e("current = " + current + ",max = " + max);
		if (current == max) {
			if (mDownLoadProgressDialog != null) {
				mDownLoadProgressDialog.dismiss();
				mDownLoadProgressDialog = null;
			}
		}
	}
}
