package google.architecture.coremodel.data.xlj;

import com.google.gson.annotations.SerializedName;

public class FootScanData {

    @SerializedName("UserId")
    private String userId;

    @SerializedName("UserSessionKey")
    private String userSessionKey;

    @SerializedName("UserSessionId")
    private String userSessionId;

    @SerializedName("UserToken")
    private FootScanToken userToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserSessionKey() {
        return userSessionKey;
    }

    public void setUserSessionKey(String userSessionKey) {
        this.userSessionKey = userSessionKey;
    }

    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    public FootScanToken getUserToken() {
        return userToken;
    }

    public void setUserToken(FootScanToken userToken) {
        this.userToken = userToken;
    }
}
