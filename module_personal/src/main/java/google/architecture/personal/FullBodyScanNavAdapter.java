package google.architecture.personal;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeAnchor;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeRule;

import google.architecture.common.widget.ScaleTransitionPagerTitleView;
import google.architecture.personal.adapter.order.MyOrderHeadAdapter;

public class FullBodyScanNavAdapter extends CommonNavigatorAdapter {

    private String[] title = {"身体参数","足型参数"};
    private IHeadTitleClickListener mHeadTitleClickListener;

    @Override
    public int getCount() {
        return title.length;
    }

    public void setHeadTitleClickListener(IHeadTitleClickListener headTitleClickListener) {
        mHeadTitleClickListener = headTitleClickListener;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int index) {
        final BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);
        ScaleTransitionPagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
        simplePagerTitleView.setMinScale(0.8f);
        simplePagerTitleView.setText(title[index]);
        simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        simplePagerTitleView.setNormalColor(ContextCompat.getColor(context, R.color.color_282828));
        simplePagerTitleView.setSelectedColor(ContextCompat.getColor(context, R.color.black));
        simplePagerTitleView.setOnClickListener(v -> {
            if(mHeadTitleClickListener != null) {
                mHeadTitleClickListener.onHeadTitleClickListener(v, index);
            }
        });
        badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);
        if (index == 1) {
            badgePagerTitleView.setXBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_RIGHT, -UIUtil.dip2px(context, 6)));
            badgePagerTitleView.setYBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_TOP, 0));
        }
        // don't cancel badge when tab selected
        badgePagerTitleView.setAutoCancelBadge(false);
        return badgePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        linePagerIndicator.setColors(ContextCompat.getColor(context, R.color.color_ff3234));
        return linePagerIndicator;
    }

    public static interface IHeadTitleClickListener {
        void onHeadTitleClickListener(View view, int index);
    }

}
