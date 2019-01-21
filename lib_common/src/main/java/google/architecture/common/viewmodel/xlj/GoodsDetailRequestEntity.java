package google.architecture.common.viewmodel.xlj;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import google.architecture.common.base.BaseApplication;

public class GoodsDetailRequestEntity {
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("appType")
    private String appType;
    @SerializedName("appToken")
    private String appToken;
    @SerializedName("method")
    private String method;
    @SerializedName("goodsId")
    private String goodsId;
    @SerializedName("productNo")
    private String productNo;
    @SerializedName("wxunId")
    private String wxunId;
    @SerializedName("mobile")
    private String mobile;

    public static String getRequestJson(String goodsId, String productNo) {
        Gson gson = new Gson();
        GoodsDetailRequestEntity goodsDetailRequestEntity = new GoodsDetailRequestEntity();
        goodsDetailRequestEntity.setUuid(BaseApplication.getIns().getmUserAccessToken());
        goodsDetailRequestEntity.setAppType("android");
        goodsDetailRequestEntity.setAppToken("y7w7jkt12E6I3BM9");
        goodsDetailRequestEntity.setMethod("Goods/goodsDetail");
        goodsDetailRequestEntity.setWxunId(BaseApplication.getIns().getmUserAccessToken() != null ? BaseApplication.getIns().getmUserAccessToken():"");
        goodsDetailRequestEntity.setMobile(BaseApplication.getIns().getmScPhoneNum() != null ? BaseApplication.getIns().getmScPhoneNum():"");
        if(!TextUtils.isEmpty(productNo)) {
            goodsDetailRequestEntity.setProductNo(productNo);
        }
        if(!TextUtils.isEmpty(goodsId)) {
            goodsDetailRequestEntity.setGoodsId(goodsId);
        }
        return gson.toJson(goodsDetailRequestEntity);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getWxunId() {
        return wxunId;
    }

    public void setWxunId(String wxunId) {
        this.wxunId = wxunId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
