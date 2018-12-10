package google.architecture.common.base.top;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import google.architecture.common.R;
import google.architecture.common.base.top.options.ToolbarOptions;

class ToolbarHelperImpl extends BaseToolBarHelperImpl {
    private TextView mLeftTextView;
    private TextView mRightTextView;
    private ImageButton mLeftButton;
    private ImageButton mRightButton;
    private TextView mTitleView;
    private String mLeftText;
    private String mRightText;
    private Drawable mLeftDrawable;
    private Drawable mRightDrawable;
    private int mTitleSize;
    @ColorInt
    private int mTitleColor;
    @ColorInt
    private int mOtherTextColor;
    private int mOtherTextSize;


    ToolbarHelperImpl(ToolbarView uiView, View rootView, @LayoutRes int toolbar) {
        super(uiView, rootView, toolbar);
    }

    @Override
    public void initToolbar() {
        mLeftTextView = (TextView) mToolbar.findViewById(R.id.tv_left);
        mRightTextView = (TextView) mToolbar.findViewById(R.id.tv_right);
        mLeftButton = (ImageButton) mToolbar.findViewById(R.id.ib_left);
        mRightButton = (ImageButton) mToolbar.findViewById(R.id.ib_right);
        mTitleView = (TextView) mToolbar.findViewById(R.id.tv_title);
        ToolbarHelper.SimpleInitToolbar(mToolbarView.getContext(), mToolbar, false);
    }

    @Override
    public void setToolbarOptions(ToolbarOptions options) {
        super.setToolbarOptions(options);
        mTitleSize = options.getTitleSize();
        mTitleColor = options.getTitleColor();
        mOtherTextColor = options.getOtherTextColor();
        mOtherTextSize = options.getOtherTextSize();
        boolean noBack = options.isNoBack();
        if (mOtherTextSize != 0) {
            if(mLeftTextView != null) mLeftTextView.setTextSize(mOtherTextSize);
            if(mRightTextView != null) mRightTextView.setTextSize(mOtherTextSize);
        }
        if (mOtherTextColor != 0) {
            if(mLeftTextView != null) mLeftTextView.setTextColor(mOtherTextColor);
            if(mRightTextView != null) mRightTextView.setTextColor(mOtherTextColor);
        }
        if (mTitleSize != 0) {
            if(mTitleView != null) mTitleView.setTextSize(mTitleSize);
        }
        if (mTitleColor != 0) {
            if(mTitleView != null) mTitleView.setTextColor(mTitleColor);
        }
        if (!noBack) {
            int backDrawable = options.getBackDrawable();
            if (backDrawable == 0) {
                backDrawable = R.drawable.ic_arrow_back;
            }
            setLeftButton(backDrawable, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mToolbarView.onBack();
                }
            });
        }
    }

    @Override
    public void setTextSize(int size) {
        if(mLeftTextView != null) mLeftTextView.setTextSize(size);
        if(mRightTextView != null) mRightTextView.setTextSize(size);
    }

    @Override
    public void setTitleSize(int size) {
        if(mTitleView != null) mTitleView.setTextSize(size);
    }

    public void setTitle(@NonNull String titleView) {
        if(mTitleView != null) mTitleView.setText(titleView);
    }

    @Override
    public void setTitle(int titleId) {
        String title = mToolbarView.getContext().getResources().getString(titleId);
        setTitle(title);
    }


    @Override
    public void setLeftText(@NonNull String str, View.OnClickListener clickListener) {
        mLeftDrawable = null;
        mLeftText = str;
        if(mLeftTextView != null) mLeftTextView.setVisibility(View.VISIBLE);
        if(mLeftTextView != null) mLeftTextView.setText(str);
        if(mLeftTextView != null) mLeftTextView.setOnClickListener(clickListener);
    }

    @Override
    public void setLeftText(@StringRes int strId, View.OnClickListener clickListener) {
        String string = mToolbarView.getContext().getResources().getString(strId);
        setLeftText(string, clickListener);
    }

    @Override
    public void setLeftButton(@NonNull Drawable drawable, View.OnClickListener clickListener) {
        mLeftText = null;
        mLeftDrawable = drawable;
        if(mLeftButton != null) mLeftButton.setVisibility(View.VISIBLE);
        if(mLeftButton != null) mLeftButton.setImageDrawable(drawable);
        if(mLeftButton != null) mLeftButton.setOnClickListener(clickListener);
    }

    @Override
    public void setLeftButton(@DrawableRes int drawableId, View.OnClickListener clickListener) {
        setLeftButton(ContextCompat.getDrawable(mToolbarView.getContext(), drawableId), clickListener);
    }

    @Override
    public void setRightText(@NonNull String str, View.OnClickListener clickListener) {
        mRightDrawable = null;
        mRightText = str;
        if(mRightTextView != null) mRightTextView.setVisibility(View.VISIBLE);
        if(mRightTextView != null) mRightTextView.setText(str);
        if(mRightTextView != null) mRightTextView.setOnClickListener(clickListener);
    }

    @Override
    public void setRightText(int strId, View.OnClickListener clickListener) {
        String string = mToolbarView.getContext().getResources().getString(strId);
        setRightText(string, clickListener);
    }

    @Override
    public void setRightButton(@NonNull Drawable drawable, View.OnClickListener clickListener) {
        mRightText = null;
        mRightDrawable = drawable;
        if(mRightButton != null) mRightButton.setVisibility(View.VISIBLE);
        if(mRightButton != null) mRightButton.setImageDrawable(drawable);
        if(mRightButton != null) mRightButton.setOnClickListener(clickListener);
    }

    @Override
    public void setRightButton(int drawableId, View.OnClickListener clickListener) {
        setRightButton(ContextCompat.getDrawable(mToolbarView.getContext(), drawableId), clickListener);
    }

    @Override
    public void setCanBack(boolean canback) {
        super.setCanBack(canback);
        if(mLeftButton != null) mLeftButton.setVisibility(canback ? View.VISIBLE : View.GONE);
    }
}
