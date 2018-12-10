package com.king.android.details.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.android.details.R;
import com.king.android.details.adapter.DetailHeadAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import google.architecture.common.statusbar.StatusbarUtils;
import google.architecture.common.util.BackgroundCompat;
import google.architecture.common.util.ImageUtils;
import google.architecture.common.util.ScreenUtils;
import google.architecture.common.util.ScrollUtils;

/**
 * @author lq.zeng
 * @date 2018/6/7
 */

public class DetailHeaderView extends FrameLayout {

    private ViewGroup mHeadViewRoot, mHeadView, mHeadViewBg;
    private View statusBarView;

    private MagicIndicator magicIndicator;
    private TextView headerTitleTv;

    private DetailHeadAdapter mDetailHeadAdapter;

    private ViewGroup mHeadBackBg, mHeadShareBg, mHeadMoreBg;
    private ImageView mHeadBackIv, mHeadShareIv, mHeadMoreIv;

    private float currentAlpha, currentTagAlpha; //alpha值保存
    private int statusBarHeight; //状态栏高度
    private int headViewRootHeight;
    private int statusBarAlphaValue = 255; //状态栏Alpha值

    private boolean isSameBg, isSameTag; //设置同一个背景过滤
    private boolean isVerticalHideStatusBar; //上下方向滚动状态栏是否隐藏
    private boolean isIndicatorClickable; //是否指示器可点击
    private boolean isPromotion; //是否活动

    private OnClickListener listener;

    public DetailHeaderView(@NonNull Context context) {
        super(context);
        initView();
    }

    public DetailHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void bindToViewPager(ViewPager viewPager) {
        if(viewPager == null) return;
        ViewPagerHelper.bind(magicIndicator, viewPager);
        mDetailHeadAdapter.setHeadTitleClickListener((view, index) -> {
            if(isIndicatorClickable)
                if(viewPager != null) viewPager.setCurrentItem(index);
        });
    }

    public void setHeadViewBgRes(boolean isPromotion) {
        setHeadViewBgRes(isPromotion, 0);
    }

    public void setHeadViewBgRes(boolean isPromotion, int headViewBgRes) {
        this.isPromotion = isPromotion;
        if(headViewBgRes > 0 && isPromotion) {
            Bitmap bmp = ImageUtils.getBitmap(getResources(), headViewBgRes, ScreenUtils.getScreenWidth(), headViewRootHeight);
            bmp = ImageUtils.scale(bmp, ScreenUtils.getScreenWidth(), headViewRootHeight);
            if(bmp != null) {
                BackgroundCompat.setBackgroundWithBitmap(mHeadViewBg, getResources(), bmp);
            } else {
                mHeadViewBg.setBackgroundResource(headViewBgRes);
            }
        } else {
            mHeadViewBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }
    }

    public void updateHead(boolean isVertical, int verticalOffset, int totalScrollRange) {
        isIndicatorClickable =  Math.abs(verticalOffset) > statusBarHeight / 2;
        if(!isPromotion) {
            mHeadView.setBackgroundColor(ScrollUtils.getColorWithAlpha(getAlpha(isVertical, verticalOffset, totalScrollRange),
                    ContextCompat.getColor(getContext(), R.color.colorPrimary)));
        } else {
            statusBarAlphaValue = Math.abs(verticalOffset) > statusBarHeight ? 0 : 255;
            if(isVertical) isVerticalHideStatusBar = (statusBarAlphaValue == 0);
            boolean isNeedSetStatusBar = isVertical || !isVerticalHideStatusBar;
            if(isNeedSetStatusBar) statusBarView.getBackground().mutate().setAlpha(statusBarAlphaValue);
        }
        float alpha = getAlpha(isVertical, verticalOffset, totalScrollRange); // 0~1
        if(alpha > 1) alpha = 1;
        int mutateBgAlpha = (int)(255 * alpha); // 0~255
        mHeadViewBg.getBackground().mutate().setAlpha(mutateBgAlpha); // Head背景
        magicIndicator.setAlpha(alpha); // 指示器
        float flagAlpha = getFlagAlpha(isVertical, verticalOffset, totalScrollRange / 2); // 0~1
        int mutateFlagAlpha = 255 - (int)(255 * flagAlpha); // 0~255
        setFlagState(mutateFlagAlpha); // Tag
    }

    private void initView() {
        statusBarHeight = StatusbarUtils.getStatusBarHeight(getContext());

        LayoutInflater.from(getContext()).inflate(R.layout.detail_item_head, this, true);

        statusBarView = findViewById(R.id.detail_head_statubar_space);
        ViewGroup.LayoutParams spaceLayoutParams = statusBarView.getLayoutParams();
        spaceLayoutParams.height = statusBarHeight;

        mHeadBackBg = findViewById(R.id.detail_head_back_fl);
        mHeadShareBg = findViewById(R.id.detail_head_share_fl);
        mHeadMoreBg = findViewById(R.id.detail_head_more_fl);

        mHeadBackBg.setOnClickListener(v -> {
            if(listener != null) {
                listener.onClick(v);
            }
        });

        mHeadBackIv = findViewById(R.id.detail_head_back_iv);
        mHeadShareIv = findViewById(R.id.detail_head_share_iv);
        mHeadMoreIv = findViewById(R.id.detail_head_more_iv);

        mHeadViewRoot = findViewById(R.id.detail_head_root);
        mHeadView = findViewById(R.id.detail_head_rl);
        mHeadViewBg = findViewById(R.id.detail_head_bg);

        headerTitleTv = findViewById(R.id.detail_head_title_tv);
        magicIndicator = findViewById(R.id.detail_head_indicator);
        CommonNavigator navigator = new CommonNavigator(getContext());
        navigator.setScrollPivotX(0.65f);
        mDetailHeadAdapter = new DetailHeadAdapter(getContext());
        navigator.setAdapter(mDetailHeadAdapter);
        magicIndicator.setNavigator(navigator);

        setFlagBg(R.drawable.bg_round_head_tag_bg);

        TypedArray actionbarSizeTypedArray = getContext().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        float h = actionbarSizeTypedArray.getDimension(0, 0);
        headViewRootHeight = statusBarHeight + (int) h;
        ViewGroup.LayoutParams rootLayoutParams = mHeadViewRoot.getLayoutParams();
        rootLayoutParams.height = headViewRootHeight;
    }

    private void setFlagBg(int bg) {
        mHeadBackBg.setBackgroundResource(bg);
        mHeadShareBg.setBackgroundResource(bg);
        mHeadMoreBg.setBackgroundResource(bg);
    }

    private void setFlagIcon(int... icons) {
        if(icons == null || icons.length < 3) return;
        mHeadBackIv.setImageResource(icons[0]);
        mHeadShareIv.setImageResource(icons[1]);
        mHeadMoreIv.setImageResource(icons[2]);
    }

    private void setFlagState(int alpha) {
        if(alpha <= 0) {
            alpha = Math.abs(alpha);
            isSameTag = false;
            if(!isSameBg) {
                isSameBg = true;
                setFlagIcon(R.mipmap.ic_arrow_back_white,
                        R.mipmap.ic_share,
                        R.mipmap.ic_more);
                setFlagBg(R.drawable.bg_round_head_tag_bg_trans);
            }
        } else {
            isSameBg = false;
            if(!isSameTag) {
                isSameTag = true;
                setFlagIcon(R.mipmap.ic_arrow_back_white,
                        R.mipmap.ic_share,
                        R.mipmap.ic_more);
                setFlagBg(R.drawable.bg_round_head_tag_bg);
            }
        }

        if(alpha <= 255) {
            mHeadBackBg.getBackground().mutate().setAlpha(alpha);
            mHeadShareBg.getBackground().mutate().setAlpha(alpha);
            mHeadMoreBg.getBackground().mutate().setAlpha(alpha);

            mHeadBackIv.getDrawable().mutate().setAlpha(alpha);
            mHeadShareIv.getDrawable().mutate().setAlpha(alpha);
            mHeadMoreIv.getDrawable().mutate().setAlpha(alpha);
        }
    }

    private float getAlpha(boolean isVertical, int verticalOffset, int totalScrollRange){
        float f = Math.abs((float)verticalOffset / totalScrollRange);
        if(isVertical) {
            currentAlpha = f;
            return f;
        }
        return f + currentAlpha;
    }

    private float getFlagAlpha(boolean isVertical, int verticalOffset, int totalScrollRange) {
        float f = Math.abs((float)verticalOffset / totalScrollRange);
        if(isVertical) {
            currentTagAlpha = f;
            return f;
        }
        return f + currentTagAlpha;
    }

    public void setHeadClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * 增加标题的动画
     * @param isClose
     */
    public void playHeaderTitleAnimat(boolean isClose) {
        if(isClose) {
            Animation animationB = AnimationUtils.loadAnimation(getContext(), R.anim.anim_bottom_out);
            headerTitleTv.startAnimation(animationB);
            Animation animationT = AnimationUtils.loadAnimation(getContext(), R.anim.anim_top_in);
            animationT.setAnimationListener(BListener);
            magicIndicator.startAnimation(animationT);
        } else {
            Animation animationT = AnimationUtils.loadAnimation(getContext(), R.anim.anim_top_out);
            magicIndicator.startAnimation(animationT);
            Animation animationB = AnimationUtils.loadAnimation(getContext(), R.anim.anim_bottom_in);
            animationB.setAnimationListener(TListener);
            headerTitleTv.startAnimation(animationB);
        }
    }

    private Animation.AnimationListener TListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            magicIndicator.setVisibility(View.INVISIBLE);
            headerTitleTv.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private Animation.AnimationListener BListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            magicIndicator.setVisibility(View.VISIBLE);
            headerTitleTv.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}
