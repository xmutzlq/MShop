package google.architecture.common.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;

import google.architecture.common.base.BaseApplication;
import google.architecture.common.base.listener.AppBrocastAction;
import google.architecture.coremodel.Account;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;
import google.architecture.coremodel.util.MD5Util;

/**
 * @author lq.zeng
 * @date 2018/4/18
 */

public class ModifyLoginViewModel extends UIViewModel {

    public ObservableField<String> oldPwdStr = new ObservableField<>();
    public ObservableField<String> pwdStr = new ObservableField<>();
    public ObservableField<String> pwdComfirmStr = new ObservableField<>();

    public void modifyLoginAccount() {
        if (isRunning.get()) {
            return;
        }
        String oldPwd = MD5Util.getMD5String(oldPwdStr.get());
        String pwd = MD5Util.getMD5String(pwdStr.get());
        String pwdComfirm = MD5Util.getMD5String(pwdComfirmStr.get());
        if (TextUtils.isEmpty(oldPwd)
                || TextUtils.isEmpty(pwd)
                || TextUtils.isEmpty(pwdComfirm)) {
            return;
        }

        disposable.add(subscribe(DeHongDataRepository.get().modifyLoginPasswd(
                Account.get().getAcessToken(), Account.get().getUserId(), oldPwd, pwd, pwdComfirm), (t)->logout()));
    }

    private void logout() {
        LogUtils.tag("zlq").e("logout");
        Account.get().clearUserInfo();
        BaseApplication.getIns().sendBroadcast(new Intent(AppBrocastAction.ACTION_USER_LOGIN_STATE_CHANGE));
    }
}
