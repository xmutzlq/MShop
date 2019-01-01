package google.architecture.coremodel.data.xlj.personal;

import com.google.gson.annotations.SerializedName;

public class GuideInfo {

    @SerializedName("guide_id")
    private int guide_id;
    @SerializedName("userId")
    private int userId;
    @SerializedName("guide_name")
    private String guide_name;
    @SerializedName("shopId")
    private int shopId;
    @SerializedName("shop_name")
    private String shop_name;
    @SerializedName("secret")
    private String secret;
    @SerializedName("qrcode_url")
    private String qrcode_url;
    @SerializedName("guide_status")
    private int guide_status;
    @SerializedName("create_time")
    private String create_time;
    @SerializedName("data_flag")
    private int data_flag;

    public int getGuide_id() {
        return guide_id;
    }

    public void setGuide_id(int guide_id) {
        this.guide_id = guide_id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGuide_name() {
        return guide_name;
    }

    public void setGuide_name(String guide_name) {
        this.guide_name = guide_name;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getQrcode_url() {
        return qrcode_url;
    }

    public void setQrcode_url(String qrcode_url) {
        this.qrcode_url = qrcode_url;
    }

    public int getGuide_status() {
        return guide_status;
    }

    public void setGuide_status(int guide_status) {
        this.guide_status = guide_status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getData_flag() {
        return data_flag;
    }

    public void setData_flag(int data_flag) {
        this.data_flag = data_flag;
    }
}
