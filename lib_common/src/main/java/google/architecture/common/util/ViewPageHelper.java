package google.architecture.common.util;

import android.support.v4.view.ViewPager;

import java.lang.reflect.Field;

import google.architecture.common.widget.ViewPagerScrollerFix;

/**
 * @author lq.zeng
 * @date 2018/9/14
 */

public class ViewPageHelper {

    ViewPager viewPager;
    ViewPagerScrollerFix scroller;

    public ViewPageHelper(ViewPager viewPager) {
        this.viewPager = viewPager;
        init();
    }

    public void setCurrentItem(int item){
        setCurrentItem(item,true);
    }

    public ViewPagerScrollerFix getScroller() {
        return scroller;
    }

    public void setCurrentItem(int item, boolean somoth){
        int current=viewPager.getCurrentItem();
        //如果页面相隔大于1,就设置页面切换的动画的时间为0
        if(Math.abs(current-item)>1){
            scroller.setNoDuration(true);
            viewPager.setCurrentItem(item,somoth);
            scroller.setNoDuration(false);
        }else{
            scroller.setNoDuration(false);
            viewPager.setCurrentItem(item,somoth);
        }
    }

    private void init(){
        scroller = new ViewPagerScrollerFix(viewPager.getContext());
        Class<ViewPager> cl = ViewPager.class;
        try {
            Field field=cl.getDeclaredField("mScroller");
            field.setAccessible(true);
            //利用反射设置mScroller域为自己定义的MScroller
            field.set(viewPager,scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }
}
