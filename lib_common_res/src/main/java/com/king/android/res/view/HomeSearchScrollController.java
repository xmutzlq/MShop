package com.king.android.res.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.king.android.res.R;

/**
 * @author lq.zeng
 * @date 2018/5/4
 */

public class HomeSearchScrollController extends RecyclerView.OnScrollListener{

    private Context mContext;
    private int height = 0;// 滑动开始变色的高,真实项目中此高度是由广告轮播或其他首页view高度决定
    private int overallXScroll = 0;

    private View rollView;
    private HomeSearchComponentsImp homeSearchComponentsImp;

    public HomeSearchScrollController(Context context) {
        mContext = context;
        height = context.getResources().getDimensionPixelSize(R.dimen.app_banner_distance) / 2;
    }

    public void setMaxHeight(int height) {
        this.height = height;
    }

    public int getMaxHeight() {
        return height;
    }

    public void bindRollView(View view) {
        rollView = view;
        homeSearchComponentsImp = new HomeSearchComponentsImp(mContext, rollView);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(rollView != null) {
            overallXScroll = overallXScroll + dy;// 累加y值 解决滑动一半y值为0
            if (overallXScroll <= 0) {   //设置标题的背景颜色
                homeSearchComponentsImp.onChangeActionBar(ContextCompat.getColor(mContext, R.color.transparent));
                homeSearchComponentsImp.onChangeLeft(R.mipmap.icon_scan_white, ContextCompat.getColor(mContext, R.color.white));
                rollView.setBackgroundColor(Color.argb((int) 0, 0, 0, 0));
            } else if (overallXScroll > 0 && overallXScroll <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                float scale = (float) overallXScroll / height;
                float alpha = (255 * scale);
                homeSearchComponentsImp.onChangeActionBar(Color.argb((int) alpha, 255, 200, 55));
                rollView.setBackgroundColor(Color.argb((int) alpha, 255, 200, 55));
            } else {
                homeSearchComponentsImp.onChangeActionBar(Color.argb((int) 255, 255, 200, 55));
                rollView.setBackgroundColor(Color.argb((int) 255, 255, 200, 55));
            }
        }
    }
}
