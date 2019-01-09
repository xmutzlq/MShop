package google.architecture.coremodel.data.xlj;

import com.google.gson.annotations.SerializedName;

public class FootScanToken {

    @SerializedName("access_token")
    private String access_token;

    @SerializedName("token_type")
    private String token_type;

    @SerializedName("userName")
    private String userName;

    @SerializedName("userId")
    private String userId;

    @SerializedName("sessionId")
    private String sessionId;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
