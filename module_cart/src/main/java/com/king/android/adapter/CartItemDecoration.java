package com.king.android.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

import google.architecture.common.widget.decoration.RvDecoration;
import google.architecture.coremodel.MultiItemTypeHelper;

/**
 * @author lq.zeng
 * @date 2018/5/17
 */

public class CartItemDecoration extends RvDecoration  {

    @Override
    protected void getExRvItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        BaseViewHolder viewHolder = (BaseViewHolder) parent.getChildViewHolder(view);
        if(MultiItemTypeHelper.TYPE_BUSINESSES == viewHolder.getItemViewType()) {
            outRect.set(0, 20, 0, 0);
        } else if(MultiItemTypeHelper.TYPE_BUSINESSES_GOODS == viewHolder.getItemViewType()) {
            outRect.set(0, 0, 0, 2);
        } else if(MultiItemTypeHelper.TYPE_GUESS_YOU_LIKE == viewHolder.getItemViewType()) {
            outRect.set(5, 0, 5, 10);
        }
    }
}
