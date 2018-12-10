package google.architecture.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.listener.LifeCycleListener;

/**
 * 增加activity绑定的Dialog
 * @author zlq
 */
public class BaseDialog extends Dialog implements LifeCycleListener {
	public Activity mAbsContextActivity;
	public int wScale, hScale;
	
	public BaseDialog(Context context) {
		super(context);
		init(context);
	}
	
	protected BaseDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init(context);
	}
	
	public boolean setDialogContentWith() {
		return true;
	};
	
	public boolean setDialogFullScreen() {
		return false;
	}
	
	protected boolean setDialogSizeEnable() {
		return false;
	}
	
	public BaseDialog(Context context, int theme) {
		super(context, theme);
		init(context);
	}
	
	private void init(Context context){
		if(context instanceof Activity) {
			mAbsContextActivity = (Activity) context;
		}
		setGravity(getDefaultGravity());
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	}

	public void setGravity(int gravity){
		Window window = getWindow();    
		window.setGravity(gravity);
	}
	
	private int getDefaultGravity(){
		return Gravity.CENTER;
	}
	
	@Override
	public void show() {
		super.show();
		if(setDialogContentWith()) {
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.width = getWindow().getWindowManager().getDefaultDisplay().getWidth() * 4 / 5;
			getWindow().setAttributes(lp);
		}
		
		if(setDialogSizeEnable()) {
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.width = getWindow().getWindowManager().getDefaultDisplay().getWidth() - DIPToPX(30);
			lp.height = getWindow().getWindowManager().getDefaultDisplay().getHeight() * 3 / 4;
			getWindow().setAttributes(lp);
		}
		
		if(setDialogFullScreen()) {
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.width = getWindow().getWindowManager().getDefaultDisplay().getWidth();
			lp.height = getWindow().getWindowManager().getDefaultDisplay().getHeight();
			getWindow().setAttributes(lp);
		}
		
		if(mAbsContextActivity != null){
			if(mAbsContextActivity instanceof BaseActivity){
				((BaseActivity) mAbsContextActivity).registerLifeCycleListener(this);
			}
		}
	}

	@Override
	public void dismiss() {
		super.dismiss();
		if(mAbsContextActivity != null){
			if(mAbsContextActivity instanceof BaseActivity){
				((BaseActivity) mAbsContextActivity).unregisterLifeCycleListener(this);
			}
		}
	}

	@Override
	public void onActivityDestroy() {
		if(isShowing()){
			dismiss();
		}
	}
	
	private int DIPToPX(float dipValue){ 
        final float scale = mAbsContextActivity.getResources().getDisplayMetrics().density; 
        return (int)(dipValue * scale + 0.5f);
	}
}
