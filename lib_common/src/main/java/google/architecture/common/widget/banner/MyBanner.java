package google.architecture.common.widget.banner;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.youth.banner.Banner;
import com.youth.banner.view.BannerViewPager;

import google.architecture.common.R;

/**
 * @author lq.zeng
 * @date 2018/8/17
 */

public class MyBanner extends Banner {

    private int curPosition;
    public ViewPager curViewPager;
    private boolean isSkip;

    public MyBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        curViewPager = (BannerViewPager) findViewById(R.id.bannerViewPager);
    }

    public void turnPage() {
        curViewPager.setCurrentItem(curPosition);
    }

    public void setIsSkip(boolean isSkip) {
        this.isSkip = isSkip;
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        Parcelable superData = super.onSaveInstanceState();
        bundle.putParcelable("super_data", superData);
        bundle.putInt("save_data", curPosition);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        Parcelable superData = bundle.getParcelable("super_data");
        curPosition = bundle.getInt("save_data");
        super.onRestoreInstanceState(superData);
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);

        if(isSkip) {
            isSkip = false;
            return;
        }
        curPosition = position;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 开始轮播
        startAutoPlay();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 停止轮播
        stopAutoPlay();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility == GONE) {
            // 停止轮播
            stopAutoPlay();
        } else if (visibility == VISIBLE) {
            // 开始轮播
            startAutoPlay();
        }
        super.onWindowVisibilityChanged(visibility);
    }
}
