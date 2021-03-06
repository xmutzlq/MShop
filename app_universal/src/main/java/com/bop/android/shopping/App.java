package com.bop.android.shopping;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.bop.android.shopping.service.InitializeService;
import com.king.android.res.config.ARouterPath;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import google.architecture.common.base.BaseApplication;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.Utils;
import google.architecture.coremodel.datamodel.http.ApiConstants;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.coremodel.util.PreferencesUtils;

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
        initProperties();
        ZXingLibrary.initDisplayOpinion(this);
        InitializeService.start(this);
        initUmeng();//友盟推送注册
    }

    private void initProperties(){
        String domain = PreferencesUtils.getString(getApplicationContext(),"domain_address","");
        int idleTime = PreferencesUtils.getInt(getApplicationContext(),"idle_time", 0);
        String xljImgPath = PreferencesUtils.getString(getApplicationContext(),"xlj_img_address","");
        boolean isConnectFootScan = PreferencesUtils.getBoolean(getApplicationContext(),"is_connect_footscan",false);
        if(!TextUtils.isEmpty(domain)){
            ApiConstants.GankHost = domain;
        }
        if(!TextUtils.isEmpty(xljImgPath)){
            ApiConstants.XLJimgHost = xljImgPath;
        }
        if(idleTime > 0){
            ApiConstants.IDLE_SECOND = idleTime;
        }
        ApiConstants.isConnectFootScan = isConnectFootScan;
    }

    private void initUmeng(){
        String appKey = "5c271064f1f556a3ce00021c";
        String secretKey = "a14c9d5976e35fb2bce7879a88326a0a";
        UMConfigure.init(this,appKey,"Umeng",UMConfigure.DEVICE_TYPE_PHONE, secretKey);
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setResourcePackageName("cn.s.android.shopping");//为了正确的找到资源包名，为开发者提供了自定义的设置资源包的接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                setDeviceToken(deviceToken);
                TestMessageDeliver.updateDeviceToken(App.this, deviceToken);
                System.out.println("=====szq======device_token:"+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                System.out.println("=====szq======fail:s:"+s+";s1:"+s1);
            }
        });
        mPushAgent.setMessageHandler(messageHandler);
    }

    UmengMessageHandler messageHandler = new UmengMessageHandler(){
        @Override
        public void dealWithCustomMessage(Context context, UMessage uMessage) {
            new Handler(getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject json = uMessage.getRaw();
                    try {
                        String custom = json.getJSONObject("body").getString("custom");
                        String msgId = json.getJSONObject("body").getString("title");
                        TestMessageDeliver.receiveMessageCallback(msgId);
                        LogUtils.tag("zlq").e("custom = " + custom);
                        if(!TextUtils.isEmpty(getGoodsId()) && getGoodsId().equals(custom)) return;
                        setGoodsId(custom);
                        //ToastUtils.showLongToast("getMessage custom:"+custom);
                        if(!TextUtils.isEmpty(custom)) {
                            if("com.king.android.details.ActivityDetails".equals(getRunningActivityName())) {
                                Bundle bundle = new Bundle();
                                bundle.putString(CommKeyUtil.EXTRA_KEY, custom);
                                bundle.putBoolean(CommKeyUtil.EXTRA_KEY_2, true);
                                EventBus.getDefault().post(new CommEvent(CommEvent.MSG_OPEN_GOODS_DETAIL_PAGE, bundle));
                            } else {
                                ARouter.getInstance().build(ARouterPath.DetailAty)
                                        .withString(CommEvent.KEY_EXTRA_VALUE_2,custom)
                                        .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .navigation(getApplicationContext());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //ToastUtils.showLongToast("getMessage:"+json.toString());
                }
            });

        }
    };

    private String getRunningActivityName(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        LogUtils.tag("zlq").e("runningActivity = " + runningActivity);
        return runningActivity;
    }
}
