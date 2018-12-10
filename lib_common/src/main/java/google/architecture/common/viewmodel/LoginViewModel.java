package google.architecture.common.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;

import google.architecture.coremodel.Account;
import google.architecture.coremodel.data.QRCodeData;
import google.architecture.coremodel.data.S_UserInfo;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;
import google.architecture.coremodel.util.MD5Util;

/**
 * @author lq.zeng
 * @date 2018/4/11
 */

public class LoginViewModel extends UIViewModel {

    public static final String LOGIN_TYPE_QQ = "qq";
    public static final String LOGIN_TYPE_WX = "wx";
    public static final String LOGIN_TYPE_XLWB = "xlwb";

    public ObservableField<String> loginAccountStr = new ObservableField<>();
    public ObservableField<String> loginPwdStr = new ObservableField<>();
    public ObservableField<S_UserInfo> userInfo = new ObservableField<>();
    public ObservableField<QRCodeData> qrCodeData = new ObservableField<>();

    /**账户登录**/
    public void loginToAccount() {
        if (isRunning.get()) {
            return;
        }
        String account = loginAccountStr.get();
        String pwd = MD5Util.getMD5String(loginPwdStr.get());
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
            return;
        }

        disposable.add(subscribe(DeHongDataRepository.get().login(account, pwd),
                (t) -> refreshUserInfo((S_UserInfo)t)));
    }

    /**三方登录**/
    public void quickLoginToAccount(String openid, String nickname, String headUrl, String gender, String source) {
        if (isRunning.get()) {
            return;
        }
        disposable.add(subscribe(DeHongDataRepository.get().quickLogin(openid, nickname, headUrl, gender, source)));
    }

    /**扫码登录**/
    public void qrcodeLogin(String token, String state) {
        disposable.add(subscribe(DeHongDataRepository.get().qrcodeLogin(token, state,
                Account.get().getUserId()),(t) -> {
            if(t == null) return;
            qrCodeData.set((QRCodeData)t);
        }));
    }

    private void refreshUserInfo(S_UserInfo result) {
        if (result == null) {
            return;
        }
        if (result != null) {
            userInfo.set(result);
        }
    }
}
