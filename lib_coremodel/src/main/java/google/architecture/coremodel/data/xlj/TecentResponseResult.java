package google.architecture.coremodel.data.xlj;

import com.google.gson.annotations.SerializedName;

public class TecentResponseResult {
    @SerializedName("access_token")
    private String access_token;
    @SerializedName("expires_in")
    private int expires_in;
    @SerializedName("refresh_token")
    private String refresh_token;
    @SerializedName("openid")
    private String openid;
    @SerializedName("scope")
    private String scope;
    @SerializedName("unionid")
    private String unionid;
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getAccess_token() {
        return access_token;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
    public int getExpires_in() {
        return expires_in;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
    public String getRefresh_token() {
        return refresh_token;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getOpenid() {
        return openid;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
    public String getScope() {
        return scope;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
    public String getUnionid() {
        return unionid;
    }

    @Override
    public String toString() {
        return "TecentResponseResult{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", openid='" + openid + '\'' +
                ", scope='" + scope + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
    }
}
