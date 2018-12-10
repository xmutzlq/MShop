package google.architecture.common.widget.banner.recycle;

import android.content.Context;
import android.util.AttributeSet;

import google.architecture.common.widget.banner.AutoScrollViewPager;

public class RecycleAutoScrollViewPager extends AutoScrollViewPager {

	public RecycleAutoScrollViewPager(Context paramContext) {
		super(paramContext);
	}
	
	public RecycleAutoScrollViewPager(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	@Override
	protected boolean isNotScrollRecycle() {
		return false;
	}
}
