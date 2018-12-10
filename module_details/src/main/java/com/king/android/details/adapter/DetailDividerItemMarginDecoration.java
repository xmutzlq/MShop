package com.king.android.details.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import google.architecture.common.widget.decoration.Divider;
import google.architecture.common.widget.decoration.DividerBuilder;
import google.architecture.common.widget.decoration.DividerItemDecoration;

/**
 * @author lq.zeng
 * @date 2018/7/3
 */

public class DetailDividerItemMarginDecoration extends DividerItemDecoration {

    private int space;
    private boolean isRank;

    public DetailDividerItemMarginDecoration(Context context, int space) {
        super(context);
        this.space = space;
    }

    public DetailDividerItemMarginDecoration(Context context, int space, boolean isRank) {
        super(context);
        this.space = space;
        this.isRank = isRank;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        int position = parent.getChildLayoutPosition(view);
        if(position > 2) {
            outRect.top = space;
            if(isRank) {
                outRect.bottom = space;
            }
        }
    }

    @Nullable
    @Override
    public Divider getDivider(int itemPosition) {
        return  new DividerBuilder().create();
    }
}
