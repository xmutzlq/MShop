package google.architecture.coremodel;

import google.architecture.coremodel.data.S_UserInfo;

/**
 * @author lq.zeng
 * @date 2018/4/17
 */

public class Account {
    private static final String TAG = Account.class.getSimpleName();

    private static Account INSTANCE;

    private String openId;
    private String userId;
    private String userName;
    private String acessToken;
    private S_UserInfo userInfo;

    private Account() {
        openId = AccountHelper.getOpenId();
        userId = AccountHelper.getUserId();
        userName = AccountHelper.getUserName();
        acessToken = AccountHelper.getAcessToken();
    }

    public static Account get() {
        if (INSTANCE == null) {
            synchronized (Account.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Account();
                }
            }
        }
        return INSTANCE;
    }

    public boolean isLogin() {
        return userInfo != null;
    }

    public void initUserInfo() {
        AccountHelper.initUserInfo();
    }

    public void setUserInfo(S_UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public S_UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfoToLocal(S_UserInfo userInfo) {

        this.userInfo = userInfo;
        if (userInfo != null) {
            setOpenIdToLocal(userInfo == null ? "" : userInfo.openid);
            setUserIdToLocal(userInfo == null ? "" : userInfo.user_id);
            setUserNameToLocal(userInfo == null ? "" : userInfo.username);
            setAcessTokenToLocal(userInfo == null ? "": userInfo.access_token);
        }
        AccountHelper.setUserInfoToLocal(userInfo);
    }

    public void clearUserInfo() {
        userInfo = null;
        openId = null;
        userName = null;
        userId = null;
        acessToken = null;
        setOpenIdToLocal("");
        setUserIdToLocal("");
        setUserNameToLocal("");
        setAcessTokenToLocal("");
        AccountHelper.clearUserInfo();
    }

    public void setOpenIdToLocal(String openId) {
        this.openId = openId;
        AccountHelper.setOpenIdToLocal(openId);
    }

    public void setUserIdToLocal(String userId) {
        this.userId = userId;
        AccountHelper.setUserIdToLocal(userId);
    }

    public void setUserNameToLocal(String userName) {
        this.userName = userName;
        AccountHelper.setUserNameToLocal(userName);
    }

    public void setAcessTokenToLocal(String acessToken) {
        this.acessToken = acessToken;
        AccountHelper.setAcessTokenToLocal(acessToken);
    }

    public String getOpenId() {
        return openId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() { return userName; }

    public String getAcessToken() {return acessToken; }
}
