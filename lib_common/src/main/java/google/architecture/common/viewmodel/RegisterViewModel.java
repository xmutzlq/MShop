package google.architecture.common.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;

import google.architecture.coremodel.data.S_UserInfo;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;
import google.architecture.coremodel.util.MD5Util;

/**
 * @author lq.zeng
 * @date 2018/4/11
 */

public class RegisterViewModel extends UIViewModel {

    public ObservableField<String> wayStr = new ObservableField<>();
    public ObservableField<String> userNameStr = new ObservableField<>();
    public ObservableField<String> userNameOnlyStr = new ObservableField<>();
    public ObservableField<String> pwdStr = new ObservableField<>();
    public ObservableField<String> codeStr = new ObservableField<>();

    public ObservableField<S_UserInfo> userInfo = new ObservableField<>();

    public void sendEmail() {
        String userName = userNameStr.get();
        if(TextUtils.isEmpty(userName)) {
            return;
        }
        disposable.add(subscribe(DeHongDataRepository.get().sendEmail("2", userName, "")));
    }

    public void sendShotMsg() {
        String userName = userNameStr.get();
        if(TextUtils.isEmpty(userName)) {
            return;
        }
        disposable.add(subscribe(DeHongDataRepository.get().sendShotMsg("2", userName, "")));
    }

    public void register() {
        if (isRunning.get()) {
            return;
        }
        String way = wayStr.get();
        String userName = userNameStr.get();
        String userNameOnly = userNameOnlyStr.get();
        String pwd = MD5Util.getMD5String(pwdStr.get());
        String code = codeStr.get();
        if (TextUtils.isEmpty(way)
                || TextUtils.isEmpty(userName)
                || TextUtils.isEmpty(userNameOnly)
                || TextUtils.isEmpty(pwd)
                || TextUtils.isEmpty(code)) {
            return;
        }

        disposable.add(subscribe(DeHongDataRepository.get().register(way, userName, userNameOnly, pwd, code),
                (t)->loginToAccount()));
    }

    private void loginToAccount() {
        String account = userNameStr.get();
        String pwd = MD5Util.getMD5String(pwdStr.get());
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
            return;
        }

        disposable.add(subscribe(DeHongDataRepository.get().login(account, pwd),
                (t)->refreshUserInfo((S_UserInfo)t)));
    }

    private void refreshUserInfo(S_UserInfo info) {
        if (info == null) {
            return;
        }
        if (info != null) {
            userInfo.set(info);
        }
    }
}
