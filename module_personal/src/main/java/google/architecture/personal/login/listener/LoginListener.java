package google.architecture.personal.login.listener;


import google.architecture.coremodel.data.S_UserInfo;

public interface LoginListener {
    void onLoginSuc(S_UserInfo userInfo);
}
