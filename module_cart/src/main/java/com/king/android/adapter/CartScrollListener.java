package com.king.android.adapter;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * @author lq.zeng
 * @date 2018/5/18
 */

public class CartScrollListener implements NestedScrollView.OnScrollChangeListener{

    private ImageView goTopView;

    public CartScrollListener(ImageView goTopView) {
        this.goTopView = goTopView;
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if(cartScroll != null && v.getChildAt(0) instanceof RecyclerView)
            cartScroll.onScrolled((RecyclerView) v.getChildAt(0), scrollX, scrollY);

        if (scrollY > oldScrollY) {
            // 向下滑动
        }

        if (scrollY < oldScrollY) {
            // 向上滑动
        }

        if (scrollY == 0) {
            // 顶部
        }

        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            // 底部
        }
    }

    RecyclerView.OnScrollListener cartScroll = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(recyclerView == null || recyclerView.getChildCount() < 7) return;
            int headerHeight = recyclerView.getChildAt(0).getHeight() * 2;
            int itemHeight = recyclerView.getChildAt(1).getHeight() * 4;
            int height = headerHeight + itemHeight;
            if(goTopView != null) goTopView.setVisibility(dy <= height ? View.GONE : View.VISIBLE);
        }
    };
}
