package google.architecture.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;
import com.kongzue.dialog.v2.SelectDialog;
import com.tencent.mm.opensdk.diffdev.DiffDevOAuthFactory;
import com.tencent.mm.opensdk.diffdev.IDiffDevOAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseApplication;
import google.architecture.common.base.listener.AppBrocastAction;
import google.architecture.personal.databinding.ActivitySettingNewBinding;

@Route(path = ARouterPath.PersonalSettingAty)
public class ActivitySetting extends BaseActivity<ActivitySettingNewBinding> {

    private ActivitySetting _this = this;

    //微信申请到的合法appID
    private static final String APP_ID = "";

    //IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;
    private View mBtnGetDeviceToken;

    @Override
    protected int getLayout() {
        return R.layout.activity_setting_new;
    }

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanBack(true);
        setTitleName("设置");
        initBtn();
    }

    private void initBtn(){
        mBtnGetDeviceToken = findViewById(R.id.btn_get_device_token);

        //设置设备信息
        mBtnGetDeviceToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToastUtils.showLongToast(BaseApplication.getIns().getDeviceToken());
                ARouter.getInstance().build(ARouterPath.DeviceInfoAty).navigation(ActivitySetting.this);
            }
        });

        //设置域名地址
        findViewById(R.id.btn_set_domain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPath.DomainInfoAty).navigation(ActivitySetting.this);
            }
        });

        //系统空闲时间设置
        findViewById(R.id.btn_set_idletime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPath.IdleTimeAty).navigation(ActivitySetting.this);
            }
        });

        findViewById(R.id.btn_contact_me).setOnClickListener(view -> {
            ARouter.getInstance().build(ARouterPath.WeixinLoginAty).navigation(ActivitySetting.this);
        });

        findViewById(R.id.btn_login_out).setOnClickListener(view -> {
            SelectDialog.show(this_, getResources().getString(R.string.warming_tip),
                    getResources().getString(R.string.login_out_confirm), (dialog, which) -> {
                        BaseApplication.getIns().setmUserAccessToken("");
                        sendBroadcast(new Intent(AppBrocastAction.ACTION_USER_LOGIN_STATE_CHANGE));
                    });
        });

    }

    private void regToWx(){
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(_this,APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
    }

    private void init(){
        IDiffDevOAuth auth = DiffDevOAuthFactory.getDiffDevOAuth();
        //auth.auth()
    }
}
