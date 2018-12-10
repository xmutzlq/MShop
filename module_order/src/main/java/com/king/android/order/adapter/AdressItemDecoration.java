package com.king.android.order.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.king.android.order.R;

import google.architecture.common.widget.decoration.Divider;
import google.architecture.common.widget.decoration.DividerBuilder;
import google.architecture.common.widget.decoration.DividerItemDecoration;

/**
 * @author lq.zeng
 * @date 2018/9/10
 */

public class AdressItemDecoration extends DividerItemDecoration {

    private Context context;

    public AdressItemDecoration(Context context) {
        super(context);
        this.context = context;
    }

    @Nullable
    @Override
    public Divider getDivider(int itemPosition) {
        return new DividerBuilder()
                .setBottomSideLine(true, ContextCompat.getColor(context, R.color.common_bg), 10, 0, 0).create();
    }
}
