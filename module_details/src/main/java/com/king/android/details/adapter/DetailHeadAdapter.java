package com.king.android.details.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.king.android.details.R;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import google.architecture.common.widget.ScaleTransitionPagerTitleView;

/**
 * @author lq.zeng
 * @date 2018/6/7
 */

public class DetailHeadAdapter extends CommonNavigatorAdapter {

    private Context mContext;
    private String[] titles;
    private IHeadTitleClickListener mHeadTitleClickListener;

    public DetailHeadAdapter(Context context) {
        mContext = context;
        titles = context.getResources().getStringArray(R.array.detail_head_titles);
    }

    public void setHeadTitleClickListener(IHeadTitleClickListener headTitleClickListener) {
        mHeadTitleClickListener = headTitleClickListener;
    }

    @Override
    public int getCount() {
        return titles == null ? 0 : titles.length;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int index) {
        SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
        simplePagerTitleView.setText(titles[index]);
        simplePagerTitleView.setTextSize(16);
        simplePagerTitleView.setNormalColor(Color.WHITE);
        simplePagerTitleView.setSelectedColor(Color.WHITE);
        simplePagerTitleView.setOnClickListener(v -> {
            if(mHeadTitleClickListener != null) {
                mHeadTitleClickListener.onHeadTitleClickListener(v, index);
            }
        });
        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        indicator.setLineHeight(UIUtil.dip2px(context, 2));
        indicator.setLineWidth(UIUtil.dip2px(context, 30));
        indicator.setRoundRadius(UIUtil.dip2px(context, 1));
        indicator.setStartInterpolator(new AccelerateInterpolator());
        indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
        indicator.setColors(Color.WHITE);
        return indicator;
    }

    public static interface IHeadTitleClickListener {
        void onHeadTitleClickListener(View view, int index);
    }
}
