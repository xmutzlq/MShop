package google.architecture.personal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.apkfuns.logutils.LogUtils;
import com.king.android.res.config.ARouterPath;
import com.tencent.mm.opensdk.diffdev.DiffDevOAuthFactory;
import com.tencent.mm.opensdk.diffdev.IDiffDevOAuth;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseApplication;
import google.architecture.common.base.ViewManager;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.PersonalViewNewModel;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.coremodel.util.EncryptUtils;
import google.architecture.coremodel.util.PreferencesUtils;
import google.architecture.personal.databinding.ActivityWeixinLoginBinding;

@Route(path = ARouterPath.WeixinLoginAty)
public class ActivityWeixinLogin extends BaseActivity<ActivityWeixinLoginBinding> implements PersonalViewNewModel.INotifyQrCodeState {

    private final String appId = "wxc4f347d14bc9bcfd";
    private final String appsecret = "2d310e98be46834077599ed3c53914e8";

    private PersonalViewNewModel mViewModel;
    private IDiffDevOAuth auth;

    @Override
    protected int getLayout() {
        return R.layout.activity_weixin_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new PersonalViewNewModel();
        mViewModel.setNotifyQrCodeState(this);
        addRunStatusChangeCallBack(mViewModel);

        load();

        findViewById(R.id.qrcode_retry_iv).setOnClickListener(v -> {
            load();
        });
    }

    @Override
    protected void onDataResult(Object o) {
        super.onDataResult(o);
        getOAuth();
    }

    private void load() {
        if(mViewModel.isTicketExp()) {
            Map<String,String> params = new HashMap<>();
            params.put("grant_type","client_credential");
            params.put("appid",appId);
            params.put("secret",appsecret);
            mViewModel.getTecentAccessToken(params);
        } else {
            getOAuth();
        }
    }

    private void getOAuth() {
        String noncestr = "noncestr" + System.currentTimeMillis();
        String sdk_ticket = PreferencesUtils.getString(this_, PersonalViewNewModel.TecentTicket);
        String timestamp = System.currentTimeMillis() + "";

        Map<String,Object> map = new HashMap<>();
        map.put("appid ",appId);
        map.put("noncestr",noncestr);
        map.put("sdk_ticket",sdk_ticket);
        map.put("timestamp",timestamp);

        String string1 = String.format("appid=%s&noncestr=%s&sdk_ticket=%s&timestamp=%s",
                appId, noncestr, sdk_ticket, timestamp);
        String signature = EncryptUtils.encryptSHA1ToString(string1);
        initOAuth(noncestr, timestamp, signature);
    }

    private void initOAuth(String noncestr,String timestamp,String signature){
        LogUtils.tag("zlq").e("message = auth.auth, signature = " + signature);
        auth = DiffDevOAuthFactory.getDiffDevOAuth();
        auth.auth(appId, "snsapi_userinfo", noncestr, timestamp, signature, new OAuthListener() {
            @Override
            public void onAuthGotQrcode(String s, byte[] bytes) {
                BaseApplication.getHandler().post(()->{
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    BitmapDrawable drawable = new BitmapDrawable(bitmap);
                    findViewById(R.id.qrcode_img_iv).setBackground(drawable);
                    reSetQrCode(true);
                });
            }

            @Override
            public void onQrcodeScanned() {
                LogUtils.tag("zlq").e("message = onQrcodeScanned");
            }

            /**
             * nooping fail, errCode = OAuthErrCode:-5, uuidStatusCode = 402 二维码过期
             */
            @Override
            public void onAuthFinish(OAuthErrCode oAuthErrCode, String s) {
                LogUtils.tag("zlq").e("message = onAuthFinish, errCode = " + oAuthErrCode.getCode() + "s = " + s);
                BaseApplication.getHandler().post(()->{
                    if(oAuthErrCode.getCode() == -5) {
                        ToastUtils.showShortToast("二维码已过期，请重试");
                        reSetQrCode(false);
                        clearAuthRequest();
                    } else {
                        if(!TextUtils.isEmpty(s)) {
                            mViewModel.getTencentWxOpenId(appId, appsecret, s, t -> {
                                EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_UPDATE_USER_INFO));
                                ViewManager.getInstance().finishActivity();
                            });
                        }
                    }
                });
            }
        });
    }

    private void reSetQrCode(boolean haveQrCode) {
        findViewById(R.id.qrcode_retry_iv).setVisibility(haveQrCode ? View.GONE : View.VISIBLE);
        findViewById(R.id.qrcode_img_iv).setVisibility(haveQrCode ? View.VISIBLE : View.GONE);
    }

    @Override
    public void notifyQrCodeState(int state) {
        reSetQrCode(state == 0);
    }

    @Override
    protected void onDestroy() {
        clearAuthRequest();
        super.onDestroy();
    }

    private void clearAuthRequest() {
        if(auth != null) {
            if(auth.stopAuth()) {
                auth.detach();
            }
        }
    }
}
