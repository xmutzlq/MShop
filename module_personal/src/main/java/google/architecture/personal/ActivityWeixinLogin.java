package google.architecture.personal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.apkfuns.logutils.LogUtils;
import com.king.android.res.config.ARouterPath;
import com.tencent.mm.opensdk.diffdev.DiffDevOAuthFactory;
import com.tencent.mm.opensdk.diffdev.IDiffDevOAuth;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;

import java.security.DigestException;
import java.util.HashMap;
import java.util.Map;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseApplication;
import google.architecture.common.base.ViewManager;
import google.architecture.common.util.Sha1;
import google.architecture.common.viewmodel.PersonalViewNewModel;
import google.architecture.common.viewmodel.UIViewModel;
import google.architecture.coremodel.data.xlj.TecentResponseResult;
import google.architecture.coremodel.util.EncryptUtils;
import google.architecture.coremodel.util.PreferencesUtils;
import google.architecture.personal.databinding.ActivityWeixinLoginBinding;

@Route(path = ARouterPath.WeixinLoginAty)
public class ActivityWeixinLogin extends BaseActivity<ActivityWeixinLoginBinding> {

    private final String appId = "wxc4f347d14bc9bcfd";
    private final String appsecret = "2d310e98be46834077599ed3c53914e8";

    private PersonalViewNewModel mViewModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_weixin_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new PersonalViewNewModel();
        addRunStatusChangeCallBack(mViewModel);
        Map<String,String> params = new HashMap<>();
        params.put("grant_type","client_credential");
        params.put("appid",appId);
        params.put("secret",appsecret);
        mViewModel.getTecentAccessToken(params);
    }

    @Override
    protected void onDataResult(Object o) {
        super.onDataResult(o);

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
        String signature2 = null;
        LogUtils.tag("zlq").e("signature = " + signature);
        try {
            signature2 = Sha1.SHA1(map);
            LogUtils.tag("zlq").e("signature2 = " + signature2);
        } catch (DigestException e) {
            e.printStackTrace();
        }
        initOAuth(noncestr, timestamp, signature);
    }

    private void initOAuth(String noncestr,String timestamp,String signature){
        IDiffDevOAuth auth = DiffDevOAuthFactory.getDiffDevOAuth();
        auth.auth(appId, "snsapi_userinfo", noncestr, timestamp, signature, new OAuthListener() {
            @Override
            public void onAuthGotQrcode(String s, byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                BitmapDrawable drawable = new BitmapDrawable(bitmap);
                ((ImageView)findViewById(R.id.qrcode_img_iv)).setBackground(drawable);
            }

            @Override
            public void onQrcodeScanned() {
                System.out.println("======szq========:onQrcodeScanned");
            }

            @Override
            public void onAuthFinish(OAuthErrCode oAuthErrCode, String s) {
                System.out.println("======szq========:oAuthErrCode:"+oAuthErrCode+"s:"+s);
                BaseApplication.getHandler().post(()->{
                    if(!TextUtils.isEmpty(s)) {
                        mViewModel.getTencentWxOpenId(appId, appsecret, s, t -> {
                            ViewManager.getInstance().finishActivity();
                        });
                    }
                });
            }
        });
    }

}
