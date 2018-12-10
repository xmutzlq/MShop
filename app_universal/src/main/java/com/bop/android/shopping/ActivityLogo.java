package com.bop.android.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bop.android.shopping.databinding.ActivityLogoBinding;
import com.king.android.res.config.ARouterPath;

import google.architecture.common.base.BaseActivity;

/**
 * @author lq.zeng
 * @date 2018/4/28
 */

public class ActivityLogo extends BaseActivity<ActivityLogoBinding> {

    @Override
    protected int getLayout() {
        return R.layout.activity_logo;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ARouter.getInstance()
                .build(ARouterPath.AppMainAty)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .withTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                .navigation(this_);
        finish();
    }


}
