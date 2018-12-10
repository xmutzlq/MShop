package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/5/3
 */

public class QRCodeData {
    @SerializedName("errcode")
    public String errcode;
    @SerializedName("errmsg")
    public String errmsg;
    @SerializedName("uid")
    public String uid;
    @SerializedName("user_token")
    public String user_token;
}
