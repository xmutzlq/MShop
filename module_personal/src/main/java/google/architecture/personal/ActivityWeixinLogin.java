package google.architecture.personal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;
import com.tencent.mm.opensdk.diffdev.DiffDevOAuthFactory;
import com.tencent.mm.opensdk.diffdev.IDiffDevOAuth;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;

import java.security.DigestException;
import java.util.HashMap;
import java.util.Map;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.util.Sha1;
import google.architecture.common.viewmodel.PersonalViewNewModel;
import google.architecture.coremodel.data.xlj.TecentTicket;
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

        String timestamp = System.currentTimeMillis()+"";
        String noncestr = "noncestr"+timestamp;

        TecentTicket ticket = (TecentTicket)o;
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("appId ",appId);
            map.put("noncestr",noncestr);
            map.put("sdk_ticket",ticket.getTicket());
            map.put("timestamp",timestamp+"");

            String signature = Sha1.SHA1(map);
            initOAuth(""/*noncestr*/, ""/*timestamp*/, ""/*signature*/);
        } catch (DigestException e) {
            e.printStackTrace();
        }
    }

    private void initOAuth(String noncestr,String timestamp,String signature){
        IDiffDevOAuth auth = DiffDevOAuthFactory.getDiffDevOAuth();
        auth.auth(appId, "snsapi_login", noncestr, timestamp, signature, new OAuthListener() {
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
            }
        });
    }

}
