package com.king.android.details.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.king.android.details.R;

import google.architecture.common.widget.decoration.Divider;
import google.architecture.common.widget.decoration.DividerBuilder;
import google.architecture.common.widget.decoration.DividerItemDecoration;

/**
 * @author lq.zeng
 * @date 2018/6/27
 */

public class DetailDividerItemDecoration extends DividerItemDecoration {

    private Context context;

    public DetailDividerItemDecoration(Context context) {
        super(context);
        this.context = context;
    }

    @Nullable
    @Override
    public Divider getDivider(int itemPosition) {
        return new DividerBuilder()
                .setLeftSideLine(true, ContextCompat.getColor(context, R.color.transparent), 3, 0, 0)
                .setRightSideLine(true, ContextCompat.getColor(context, R.color.transparent), 3, 0, 0).create();
    }
}
