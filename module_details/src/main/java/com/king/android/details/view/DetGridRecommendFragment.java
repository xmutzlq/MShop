package com.king.android.details.view;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;

import google.architecture.common.base.BaseFragment;

/**
 * @author lq.zeng
 * @date 2018/7/2
 */
@Route(path = ARouterPath.DetailGridRecommendFgt)
public class DetGridRecommendFragment extends BaseFragment {

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected int getLayout() {
        return 0;
    }
}
