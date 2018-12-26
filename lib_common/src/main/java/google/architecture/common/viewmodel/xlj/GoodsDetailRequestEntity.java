package google.architecture.common.viewmodel.xlj;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class GoodsDetailRequestEntity {
    @SerializedName("appType")
    private String appType;
    @SerializedName("appToken")
    private String appToken;
    @SerializedName("method")
    private String method;
    @SerializedName("goodsId")
    private String goodsId;

    public static String getRequestJson(String goodsId) {
        Gson gson = new Gson();
        GoodsDetailRequestEntity goodsDetailRequestEntity = new GoodsDetailRequestEntity();
        goodsDetailRequestEntity.setAppType("android");
        goodsDetailRequestEntity.setAppToken("y7w7jkt12E6I3BM9");
        goodsDetailRequestEntity.setMethod("Goods/goodsDetail");
        goodsDetailRequestEntity.setGoodsId(goodsId);
        return gson.toJson(goodsDetailRequestEntity);
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
