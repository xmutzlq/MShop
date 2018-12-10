package google.architecture.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import google.architecture.common.R;

public class DialogsUtil {

	/**
	 * 显示升级对话框
	 * 
	 * @param activity
	 * @param updateInfoStr
	 * @param comfirmListener
	 * @param closeListener
	 */
	public static void showUpdateDialog(Activity activity, String updateInfoStr, boolean isForceUpdate,
			final OnClickListener comfirmListener, final OnClickListener closeListener) {
		final BaseDialog dialog = new CustomDialog(activity, R.style.MyDialogStyle);
		View view = View.inflate(activity, R.layout.update_dialog_layout, null);
		TextView updateComfirmTV = view.findViewById(R.id.update_comfirm_tv);
		ImageView updateCloseIV = view.findViewById(R.id.update_close_iv);
		LinearLayout listLL = view.findViewById(R.id.list_ll);
		LayoutInflater inflater = activity.getLayoutInflater();
		String[] updatelist = updateInfoStr.split("\n");
		for (int i=0;i<updatelist.length;i++){
			View itemView = inflater.inflate(R.layout.item_update_layout, listLL, false);
			TextView contentTV = itemView.findViewById(R.id.content);
			contentTV.setText(updatelist[i]);
			listLL.addView(itemView);
		}


		if (isForceUpdate) updateCloseIV.setVisibility(View.GONE);
		updateComfirmTV.setOnClickListener(v -> {
			dialog.dismiss();
			if (comfirmListener != null) {
				comfirmListener.onClick(v);
			}
		});
		updateCloseIV.setOnClickListener(v -> {
			dialog.dismiss();
			if (closeListener != null) {
				closeListener.onClick(v);
			}
		});

		dialog.setContentView(view);
		dialog.setCancelable(false);
		dialog.setOnKeyListener((dialog1, keyCode, event) -> {
			if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK) {
				return true;
			}
			return false;
		});

		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	/**
	 * 显示现在进度对话框
	 *
	 * @param context
	 * @return
	 */
	public static Dialog buildDownloadStatusDialog(final Context context) {
		final BaseDialog dialog = new CustomDialog(context, R.style.MyDialogStyle);
		View view = View.inflate(context, R.layout.dialog_download_lay, null);
		dialog.setContentView(view);
		return dialog;
	}
	
	/**
	 * 通用提示对话框
	 * 
	 * @param context
	 */
	public static Dialog commTipDialog(final Context context, String tipStr, final OnClickListener cancelListener,
			final OnClickListener commitListener) {
		return commTipDialog(context, "提示", tipStr, "确定", "取消", cancelListener, commitListener);
	}

	public static Dialog commTipDialog(final Context context, String tipStr, String commitBtnStr, String cancelBtnStr,
			final OnClickListener cancelListener, final OnClickListener commitListener) {
		return commTipDialog(context, "提示", tipStr, commitBtnStr, cancelBtnStr, cancelListener, commitListener);
	}

	public static Dialog commTipDialog(final Context context, String titleStr, String tipStr, String commitBtnStr,
			String cancelBtnStr, final OnClickListener cancelListener, final OnClickListener commitListener) {
		if (context == null) return null;
		final BaseDialog dialog = customDialog(context, R.style.MyDialogStyle);
		final View view = View.inflate(context, R.layout.dialog_layout, null);
		dialog.setContentView(view);
		final TextView titleTv = view.findViewById(R.id.title);
		final TextView tipTv = view.findViewById(R.id.message);
		final Button exitBtn = view.findViewById(R.id.btn_sure);
		final Button cancelBtn = view.findViewById(R.id.btn_cancel);
		final TextView lineTV = view.findViewById(R.id.dialog_line_tv);

		if (!TextUtils.isEmpty(commitBtnStr)) {
			exitBtn.setText(commitBtnStr);
		} else {
			lineTV.setVisibility(View.GONE);
			exitBtn.setVisibility(View.GONE);
		}

		if (!TextUtils.isEmpty(cancelBtnStr)) {
			cancelBtn.setText(cancelBtnStr);
		} else {
			lineTV.setVisibility(View.GONE);
			cancelBtn.setVisibility(View.GONE);
		}

		if (!TextUtils.isEmpty(titleStr)) {
			titleTv.setText(titleStr);
		} else {
			titleTv.setVisibility(View.GONE);
		}

		if (!TextUtils.isEmpty(tipStr)) {
			tipTv.setText(tipStr);
		}

		exitBtn.setOnClickListener(v -> {
			dialog.dismiss();
			if (commitListener != null) {
				commitListener.onClick(v);
			}
		});

		cancelBtn.setOnClickListener(v -> {
			dialog.dismiss();
			if (cancelListener != null) {
				cancelListener.onClick(v);
			}
		});

		if (!dialog.isShowing()) {
			dialog.show();
		}

		return dialog;
	}
	
    /**
     * 确认对话框
     *
     * @param context
     * @param contentStr
     */
    public static void showConfirmDialog(final Context context,String contentStr) {
		showConfirmDialog(context,contentStr,"","",null);
    }

	/**
	 * 确认对话框
	 *
	 * @param context
	 * @param contentStr
	 */
	public static void showConfirmDialog(final Context context,String contentStr,String title,String btnStr,final OnClickListener sureListener) {
		if (context == null)
			return;
		final BaseDialog dialog = customDialog(context, R.style.MyDialogStyle);
		final View view = View.inflate(context, R.layout.dialog_confirm_layout, null);
		final TextView content = (TextView) view.findViewById(R.id.content);
		TextView titleTV = (TextView) view.findViewById(R.id.title);
		if (!TextUtils.isEmpty(title)){
			titleTV.setText(title);
			titleTV.setVisibility(View.VISIBLE);
		}
		final TextView sureBtn = (TextView) view.findViewById(R.id.confirm);
		if (!TextUtils.isEmpty(btnStr)){
			sureBtn.setText(btnStr);
		}
		content.setText(contentStr);
		sureBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				if (sureListener!=null){
					sureListener.onClick(v);
				}
			}
		});
		dialog.setContentView(view);
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	/**
	 * BaseDialog
	 * 
	 * @param context
	 * @param theme
	 * @return
	 */
	public static final BaseDialog customDialog(Context context, int theme) {
		BaseDialog dialog = new BaseDialog(context, theme);
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}

}
