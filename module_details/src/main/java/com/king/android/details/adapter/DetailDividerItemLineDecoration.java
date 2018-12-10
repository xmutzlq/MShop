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
 * @date 2018/6/28
 */

public class DetailDividerItemLineDecoration extends DividerItemDecoration {

    private Context context;

    public DetailDividerItemLineDecoration(Context context) {
        super(context);
        this.context = context;
    }

    @Nullable
    @Override
    public Divider getDivider(int itemPosition) {
        return new DividerBuilder()
                .setTopSideLine(true, ContextCompat.getColor(context, R.color.common_bg), 1, 10, 0).create();
    }
}
