package com.king.android.details.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.details.R;
import com.king.android.res.config.ARouterPath;

import google.architecture.common.base.BaseFragment;

/**
 * @author lq.zeng
 * @date 2018/6/8
 */
@Route(path = ARouterPath.DetailRecommendFgt)
public class DetRecommendFragment extends BaseFragment{

    @Override
    protected boolean isStatusBarTransparent() {
        return false;
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_det_recommend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
