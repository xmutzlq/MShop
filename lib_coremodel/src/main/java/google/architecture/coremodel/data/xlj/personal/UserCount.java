package google.architecture.coremodel.data.xlj.personal;

import com.google.gson.annotations.SerializedName;

public class UserCount {

    @SerializedName("waitPay")
    private int waitPay;//待付款
    @SerializedName("waitDeliver")
    private int waitDeliver;//待发送
    @SerializedName("waitReceive")
    private int waitReceive;//待收货
    @SerializedName("waitAppraise")
    private int waitAppraise;//待评价
    @SerializedName("refund")
    private int refund;//退换货
    @SerializedName("couponCount")
    private int couponCount;//优惠券
    @SerializedName("drinkCount")
    private int drinkCount;//饮品券

    public int getWaitPay() {
        return waitPay;
    }

    public void setWaitPay(int waitPay) {
        this.waitPay = waitPay;
    }

    public int getWaitDeliver() {
        return waitDeliver;
    }

    public void setWaitDeliver(int waitDeliver) {
        this.waitDeliver = waitDeliver;
    }

    public int getWaitReceive() {
        return waitReceive;
    }

    public void setWaitReceive(int waitReceive) {
        this.waitReceive = waitReceive;
    }

    public int getWaitAppraise() {
        return waitAppraise;
    }

    public void setWaitAppraise(int waitAppraise) {
        this.waitAppraise = waitAppraise;
    }

    public int getRefund() {
        return refund;
    }

    public void setRefund(int refund) {
        this.refund = refund;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public int getDrinkCount() {
        return drinkCount;
    }

    public void setDrinkCount(int drinkCount) {
        this.drinkCount = drinkCount;
    }
}
