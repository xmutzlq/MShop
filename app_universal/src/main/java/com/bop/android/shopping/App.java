package com.bop.android.shopping;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bop.android.shopping.service.InitializeService;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import google.architecture.common.base.BaseApplication;
import google.architecture.common.util.Utils;

public class App  extends BaseApplication{

    @Override
    public void onCreate() {
        super.onCreate();

        if (Utils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        ZXingLibrary.initDisplayOpinion(this);
        InitializeService.start(this);
    }
}
