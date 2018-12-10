package google.architecture.common.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;

import google.architecture.common.base.BaseApplication;
import google.architecture.common.base.listener.AppBrocastAction;
import google.architecture.coremodel.Account;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/4/23
 */

public class ModifyUserAccountViewModel extends UIViewModel {

    public ObservableField<String> wayStr = new ObservableField<>();
    public ObservableField<String> userNameStr = new ObservableField<>();
    public ObservableField<String> userNameStr2 = new ObservableField<>();
    public ObservableField<String> userNameStr3 = new ObservableField<>();
    public ObservableField<String> codeStr = new ObservableField<>();

    public ObservableField<Boolean> modifyResultNotify = new ObservableField<>();

    public void sendEmail() {
        String userName = userNameStr.get();
        if(TextUtils.isEmpty(userName)) {
            return;
        }
        disposable.add(subscribe(DeHongDataRepository.get().sendEmail("5", userName, Account.get().getUserId())));
    }

    public void sendShotMsg() {
        String userName = userNameStr.get();
        if(TextUtils.isEmpty(userName)) {
            return;
        }
        disposable.add(subscribe(DeHongDataRepository.get().sendShotMsg("5", userName, Account.get().getUserId())));
    }

    public void modifyLoginPsw() {
        if (isRunning.get()) {
            return;
        }
        String oldPsw = userNameStr.get();
        String newPsw = userNameStr2.get();
        String confirmPsw = userNameStr3.get();
        if (TextUtils.isEmpty(oldPsw)
                || TextUtils.isEmpty(newPsw)
                || TextUtils.isEmpty(confirmPsw)) {
            return;
        }
        disposable.add(subscribe(DeHongDataRepository.get().modifyLoginPasswd(
                Account.get().getAcessToken(), Account.get().getUserId(), oldPsw, newPsw, confirmPsw), (t)->logout()));
    }

    public void modifyEmailAccount() {
        if (isRunning.get()) {
            return;
        }
        String userName = userNameStr.get();
        String code = codeStr.get();
        if (TextUtils.isEmpty(userName)
                || TextUtils.isEmpty(code)) {
            return;
        }

        disposable.add(subscribe(DeHongDataRepository.get().modifyEmail(
                Account.get().getAcessToken(), Account.get().getUserId(), userName, code), (t)->{
            if(modifyResultNotify != null) modifyResultNotify.set(true);
        }));
    }

    public void modifyCellphoneAccount() {
        if (isRunning.get()) {
            return;
        }
        String userName = userNameStr.get();
        String code = codeStr.get();
        if (TextUtils.isEmpty(userName)
                || TextUtils.isEmpty(code)) {
            return;
        }

        disposable.add(subscribe(DeHongDataRepository.get().modifyCellphone(
                Account.get().getAcessToken(), Account.get().getUserId(), userName, code), (t)->{
            if(modifyResultNotify != null) modifyResultNotify.set(true);
        }));
    }

    private void logout() {
        LogUtils.tag("zlq").e("logout");
        Account.get().clearUserInfo();
        BaseApplication.getIns().sendBroadcast(new Intent(AppBrocastAction.ACTION_USER_LOGIN_STATE_CHANGE));
    }
}
