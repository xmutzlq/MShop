package google.architecture.common.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import google.architecture.common.util.ViewPageHelper;

/**
 * @author lq.zeng
 * @date 2018/9/14
 */

public class SuperViewPager extends ViewPager {

    private ViewPageHelper helper;

    public SuperViewPager(Context context) {
        this(context,null);
    }

    public SuperViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        helper = new ViewPageHelper(this);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item,true);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        ViewPagerScrollerFix scroller = helper.getScroller();
        if(Math.abs(getCurrentItem() - item) > 1){
            scroller.setNoDuration(true);
            super.setCurrentItem(item, smoothScroll);
            scroller.setNoDuration(false);
        } else {
            scroller.setNoDuration(false);
            super.setCurrentItem(item, smoothScroll);
        }
    }
}
