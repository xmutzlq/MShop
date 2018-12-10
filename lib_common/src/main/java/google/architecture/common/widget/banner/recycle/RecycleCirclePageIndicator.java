package google.architecture.common.widget.banner.recycle;


import android.content.Context;
import android.util.AttributeSet;

import google.architecture.common.widget.banner.CirclePageIndicator;

public class RecycleCirclePageIndicator extends CirclePageIndicator {

	public RecycleCirclePageIndicator(Context context) {
		super(context);
	}
	
	public RecycleCirclePageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected boolean isNotScrollRecycle() {
		return false;
	}
}
