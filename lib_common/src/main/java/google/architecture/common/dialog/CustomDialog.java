package google.architecture.common.dialog;

import android.content.Context;

public class CustomDialog extends BaseDialog{

	public CustomDialog(Context context) {
		super(context);
	}
	
	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	public boolean setDialogContentWith() {
		return false;
	}
}
