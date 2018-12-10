package google.architecture.personal.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import google.architecture.common.widget.ScaleTransitionPagerTitleView;
import google.architecture.personal.R;

/**
 * @author lq.zeng
 * @date 2018/6/12
 */

public class PersonalHeadAdapter extends CommonNavigatorAdapter {

    private Context mContext;
    private String[] titles;
    private IHeadTitleClickListener mHeadTitleClickListener;

    public PersonalHeadAdapter(Context context, String[] titleArr) {
        mContext = context;
        titles = titleArr;
    }

    public void setHeadTitleClickListener(IHeadTitleClickListener headTitleClickListener) {
        mHeadTitleClickListener = headTitleClickListener;
    }

    @Override
    public int getCount() {
        return titles == null ? 0 : titles.length;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        ScaleTransitionPagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
        simplePagerTitleView.setMinScale(0.72f);
        simplePagerTitleView.setText(titles[index]);
        simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        simplePagerTitleView.setNormalColor(ContextCompat.getColor(context, R.color.color_282828));
        simplePagerTitleView.setSelectedColor(ContextCompat.getColor(context, R.color.black));
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHeadTitleClickListener != null) {
                    mHeadTitleClickListener.onHeadTitleClickListener(v, index);
                }
            }
        });
        return simplePagerTitleView;
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
