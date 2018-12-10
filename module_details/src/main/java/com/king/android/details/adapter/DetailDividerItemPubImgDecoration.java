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
 * @date 2018/6/27
 */

public class DetailDividerItemPubImgDecoration extends DividerItemDecoration {

    private int space;

    public DetailDividerItemPubImgDecoration(Context context, int space) {
        super(context);
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.right = space;
        outRect.left = space;
    }

    @Nullable
    @Override
    public Divider getDivider(int itemPosition) {
        return  new DividerBuilder().create();
    }
}
