package com.bop.android.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bop.android.shopping.databinding.ActivitySplashBinding;
import com.king.android.res.config.ARouterPath;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseApplication;
import google.architecture.common.base.ViewManager;

/**
 * @author lq.zeng
 * @date 2018/4/25
 */
@Route(path = ARouterPath.AppSplashAty)
public class ActivitySplash extends BaseActivity<ActivitySplashBinding>{

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);
        BaseApplication.APP_STATUS = BaseApplication.APP_STATUS_NORMAL; // App正常的启动，设置App的启动状态为正常启动
        super.onCreate(savedInstanceState);

        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                ViewManager.getInstance().finishActivity();
                return;
            }
        }

        Looper.myQueue().addIdleHandler(() -> {
            ARouter.getInstance()
                    .build(ARouterPath.AppMainAty)
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .withTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    .navigation(this_);
            ViewManager.getInstance().finishActivity();
            return false;
        });
    }
}
