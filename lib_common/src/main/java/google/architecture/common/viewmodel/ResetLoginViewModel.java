package google.architecture.common.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;

import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;
import google.architecture.coremodel.util.MD5Util;

/**
 * @author lq.zeng
 * @date 2018/4/16
 */

public class ResetLoginViewModel extends UIViewModel {

    public ObservableField<String> wayStr = new ObservableField<>();
    public ObservableField<String> userNameStr = new ObservableField<>();
    public ObservableField<String> codeStr = new ObservableField<>();
    public ObservableField<String> pwdStr = new ObservableField<>();
    public ObservableField<String> pwdComfirmStr = new ObservableField<>();

    public void sendEmail() {
        String userName = userNameStr.get();
        if(TextUtils.isEmpty(userName)) {
            return;
        }
        disposable.add(subscribe(DeHongDataRepository.get().sendEmail("4", userName, "")));
    }

    public void sendShotMsg() {
        String userName = userNameStr.get();
        if(TextUtils.isEmpty(userName)) {
            return;
        }
        disposable.add(subscribe(DeHongDataRepository.get().sendShotMsg("4", userName, "")));
    }

    public void resetLoginAccount() {
        if (isRunning.get()) {
            return;
        }
        String way = wayStr.get();
        String userName = userNameStr.get();
        String code = codeStr.get();
        String pwd = MD5Util.getMD5String(pwdStr.get());
        String pwdComfirm = MD5Util.getMD5String(pwdComfirmStr.get());
        if (TextUtils.isEmpty(way)
                || TextUtils.isEmpty(userName)
                || TextUtils.isEmpty(code)
                || TextUtils.isEmpty(pwd)
                || TextUtils.isEmpty(pwdComfirm)) {
            return;
        }

        disposable.add(subscribe(DeHongDataRepository.get().resetLoginPasswd(way, userName, code, pwd, pwdComfirm)));
    }
}
