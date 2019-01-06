package google.architecture.coremodel.data.xlj;

import com.google.gson.annotations.SerializedName;

public class TecentAccessToken {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
