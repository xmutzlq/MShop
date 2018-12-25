package google.architecture.coremodel.data.xlj.goodsdetail;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/12/25
 */

public class CouponsItem {
    @SerializedName("couponId")
    private String couponId;
    @SerializedName("shopId")
    private String shopId;
    @SerializedName("couponValue")
    private String couponValue;
    @SerializedName("useCondition")
    private String useCondition;
    @SerializedName("useMoney")
    private String useMoney;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("couponNum")
    private int couponNum;
    @SerializedName("limitNum")
    private int limitNum;
    @SerializedName("receiveNum")
    private int receiveNum;
    @SerializedName("useObjects")
    private String useObjects;
    @SerializedName("isReceive")
    private String isReceive;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(String couponValue) {
        this.couponValue = couponValue;
    }

    public String getUseCondition() {
        return useCondition;
    }

    public void setUseCondition(String useCondition) {
        this.useCondition = useCondition;
    }

    public String getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(String useMoney) {
        this.useMoney = useMoney;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(int couponNum) {
        this.couponNum = couponNum;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public int getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(int receiveNum) {
        this.receiveNum = receiveNum;
    }

    public String getUseObjects() {
        return useObjects;
    }

    public void setUseObjects(String useObjects) {
        this.useObjects = useObjects;
    }

    public String getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(String isReceive) {
        this.isReceive = isReceive;
    }
}
