package com.king.android.details.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.apkfuns.logutils.LogUtils;
import com.king.android.details.R;
import com.king.android.details.databinding.FragmentDetDetailBinding;
import com.king.android.res.config.ARouterPath;

import google.architecture.common.base.BaseFragment;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.widget.CommWebView;

/**
 * @author lq.zeng
 * @date 2018/6/8
 */
@Route(path = ARouterPath.DetailDetailFgt)
public class DetDetailFragment extends BaseFragment<FragmentDetDetailBinding> {

    public static final String DetDetailTag = "isNormal";
    private int currentPosition;
    private TextView leftBtn, rightBtn, centerBtn;
    public static String url;

    private CommWebView commWebView;

    public static final String urls[] = {
       "https://api.zdzapp.cn/webhtml/goodscontent?goods_id=1",
       "https://in.m.jd.com/product/guige/5225346.html",
        "https://in.m.jd.com/product/combine/5225346.html"
    };

    public static DetDetailFragment newInstance(String url) {
        return newInstance(true, url);
    }

    public static DetDetailFragment newInstance(boolean isNormal, String url) {
        DetDetailFragment fragment = new DetDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(DetDetailTag, isNormal);
        bundle.putString(CommKeyUtil.EXTRA_KEY, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_det_detail;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] titles = mContext.getResources().getStringArray(R.array.detail_web_titles);

        leftBtn = findViewById(view, R.id.common_3_left_btn);
        centerBtn = findViewById(view, R.id.common_3_center_btn);
        rightBtn = findViewById(view, R.id.common_3_right_btn);
        leftBtn.setText(titles[0]);
        centerBtn.setText(titles[1]);
        rightBtn.setText(titles[2]);
        leftBtn.setOnClickListener(v -> {
            if(currentPosition != 0) {
                currentPosition = 0;
                commWebView.loadUrl(urls[0]);
                changeTextColor(0);
            }
        });
        rightBtn.setOnClickListener(v -> {
            if(currentPosition != 2) {
                currentPosition = 2;
                commWebView.loadUrl(urls[2]);
                changeTextColor(2);
            }
        });
        centerBtn.setOnClickListener(v -> {
            if(currentPosition != 1) {
                currentPosition = 1;
                commWebView.loadUrl(urls[1]);
                changeTextColor(1);
            }
        });

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        commWebView = new CommWebView(mContext);
        binding.detDetailWebContainer.addView(commWebView, params);
    }

    @Override
    public void onFragmentFirstVisible() {
        if(TextUtils.isEmpty(url)) {
            url = getArguments().getString(CommKeyUtil.EXTRA_KEY);
        }
        boolean isNormal = getArguments().getBoolean(DetDetailTag, true);
        commWebView.setIsNormal(isNormal);
        commWebView.loadUrl(url);
        changeTextColor(0);
    }

    private void changeTextColor(int position) {
        switch (position) {
            case 0:
                leftBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_FD644B));
                centerBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_aaaaaa));
                rightBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_aaaaaa));
                break;
            case 1:
                leftBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_aaaaaa));
                centerBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_FD644B));
                rightBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_aaaaaa));
                break;
            case 2:
                leftBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_aaaaaa));
                centerBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_aaaaaa));
                rightBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_FD644B));
                break;
        }
    }
}
