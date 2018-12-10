package com.king.android.res.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.R;
import com.king.android.res.config.ARouterPath;
import com.king.android.res.util.DebouncingOnClickListener;
import com.king.android.res.util.DimensionsUtil;

import org.greenrobot.eventbus.EventBus;

import google.architecture.coremodel.datamodel.http.event.CommEvent;

/**
 * @author lq.zeng
 * @date 2018/5/7
 */

public class HomeSearchComponentsImp implements HomeSearchComponents {

    View rootView;
    View actionBar;
    ViewGroup left;
    ViewGroup right;
    ViewGroup center;

    public HomeSearchComponentsImp(final Context context, final View rootView) {
        this.rootView = rootView;
        actionBar = rootView.findViewById(R.id.action_bar_space);
        left = rootView.findViewById(R.id.top_left_container);
        right = rootView.findViewById(R.id.top_right_container);
        center = rootView.findViewById(R.id.search_center_lay);
        center.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                ARouter.getInstance()
                        .build(ARouterPath.SearchAty)
                        .navigation(context);
            }
        });

        View[] lefts = left(context);
        View[] rights = right(context);
        View[] centers = center(context);

        for (View view : lefts) {
            left.addView(view);
        }

        for (View view : rights) {
            right.addView(view);
        }

        center.addView(centers[0]);
        center.addView(centers[1]);

        left.setOnClickListener(v -> EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_OPEN_SCAN))); //扫一扫

        right.setOnClickListener(v -> {

        });
    }

    @Override
    public View actionBar() {
        return actionBar;
    }

    @Override
    public View root() {
        return rootView;
    }

    @Override
    public View[] left(Context context) {
        LinearLayout.LayoutParams lp
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        ImageView iv = new ImageView(context);
        iv.setImageResource(R.mipmap.icon_scan_white);
        iv.setLayoutParams(lp);
        TextView tv = new TextView(context);
        tv.setTextColor(ContextCompat.getColor(context, R.color.white));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        tv.setText(R.string.home_scan_lable);
        tv.setLayoutParams(lp);

        return new View[]{iv, tv};
    }

    @Override
    public View[] right(Context context) {
        LinearLayout.LayoutParams lp
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        ImageView iv = new ImageView(context);
        iv.setImageResource(R.mipmap.icon_message_white);
        iv.setLayoutParams(lp);
        TextView tv = new TextView(context);
        tv.setTextColor(ContextCompat.getColor(context, R.color.white));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        tv.setText(R.string.home_message_lable);
        tv.setLayoutParams(lp);
        return new View[]{iv, tv};
    }

    @Override
    public View[] center(Context context) {
        RelativeLayout.LayoutParams lp1
                = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp1.setMargins(DimensionsUtil.dip2px(context, 10), 0, 0, 0);
        lp1.addRule(RelativeLayout.CENTER_VERTICAL);
        ImageView iv = new ImageView(context);
        iv.setId(R.id.layout_add);
        iv.setImageResource(R.mipmap.icon_search_gray);
        iv.setLayoutParams(lp1);

        RelativeLayout.LayoutParams lp2
                = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp2.setMargins(DimensionsUtil.dip2px(context, 10), 0, 0, 0);
        lp2.addRule(RelativeLayout.CENTER_VERTICAL);
        lp2.addRule(RelativeLayout.RIGHT_OF, iv.getId());
        TextView tv = new TextView(context);
        tv.setTextColor(ContextCompat.getColor(context, R.color.white));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        tv.setHint(R.string.home_search_input_hint);
        tv.setLayoutParams(lp2);
        return new View[]{iv, tv};
    }

    public void onChangeActionBar(int color) {
        if(color != -1) actionBar().setBackgroundColor(color);
    }

    public void onChangeLeft(int imgRes, int textColor) {
        if(imgRes != -1) ((ImageView)left.getChildAt(0)).setImageResource(imgRes);
        if(textColor != -1) ((TextView)left.getChildAt(1)).setTextColor(textColor);
    }

    public void onChangeRight(int imgRes, int textColor) {
        if(imgRes != -1) ((ImageView)right.getChildAt(0)).setImageResource(imgRes);
        if(textColor != -1) ((TextView)right.getChildAt(1)).setTextColor(textColor);
    }

    public void onChangeCenter(int bgColor, int imgRes, int textColor) {
        if(bgColor != -1) center.setBackgroundColor(bgColor);
        if(imgRes != -1) ((ImageView)center.getChildAt(0)).setImageResource(imgRes);
        if(textColor != -1) ((TextView)center.getChildAt(1)).setTextColor(textColor);
    }
}
