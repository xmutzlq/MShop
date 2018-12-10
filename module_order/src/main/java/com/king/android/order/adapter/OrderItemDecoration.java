package com.king.android.order.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

import google.architecture.common.widget.decoration.RvDecoration;
import google.architecture.coremodel.MultiItemTypeHelper;

/**
 * @author lq.zeng
 * @date 2018/9/7
 */

public class OrderItemDecoration extends RvDecoration {
    @Override
    protected void getExRvItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        BaseViewHolder viewHolder = (BaseViewHolder) parent.getChildViewHolder(view);
        if(MultiItemTypeHelper.TYPE_ORDER_BUSINESSES_GOODS == viewHolder.getItemViewType()) {
            outRect.set(0, 0, 0, 20);
        }
    }
}
