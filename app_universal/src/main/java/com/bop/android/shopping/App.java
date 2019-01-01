package com.bop.android.shopping;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bop.android.shopping.service.InitializeService;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
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
        initUmeng();//友盟推送注册
    }

    private void initUmeng(){
        String appKey = "5c27341ab465f593a000042c";
        String secretKey = "deddcb32166de27be33c08ad4bc3f866";
        UMConfigure.init(this,appKey,"Umeng",UMConfigure.DEVICE_TYPE_PHONE, secretKey);
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                System.out.println("=====szq======device_token:"+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }
}
