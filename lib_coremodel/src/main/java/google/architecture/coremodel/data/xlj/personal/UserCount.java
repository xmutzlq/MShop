package google.architecture.coremodel.data.xlj.personal;

import com.google.gson.annotations.SerializedName;

public class UserCount {

    @SerializedName("waitPay")
    private int waitPay;
    @SerializedName("waitDeliver")
    private int waitDeliver;
    @SerializedName("waitReceive")
    private int waitReceive;
    @SerializedName("waitAppraise")
    private int waitAppraise;
    @SerializedName("refund")
    private int refund;
    @SerializedName("couponCount")
    private int couponCount;
    @SerializedName("drinkCount")
    private int drinkCount;

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
