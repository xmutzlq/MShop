package com.king.android.res.view;

import android.content.Context;
import android.view.View;

/**
 * @author lq.zeng
 * @date 2018/5/7
 */

public interface HomeSearchComponents {
    public View actionBar();
    public View root();
    public View[] left(Context context);
    public View[] right(Context context);
    public View[] center(Context context);
}
