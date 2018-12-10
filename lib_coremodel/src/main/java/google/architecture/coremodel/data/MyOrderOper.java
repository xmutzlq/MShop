package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/9/13
 */

public class MyOrderOper {
    public static final int FLAG_CANCEL = 1; //取消订单
    public static final int FLAG_PAY = 2; //去支付
    public static final int FLAG_DEL = 3; //删除订单
    public static final int FLAG_COMMENT = 4; //评价
    public static final int FLAG_LOGISTICAL = 5; //查看物流
    public static final int FLAG_SEND = 6; //提醒发货
    public static final int FLAG_CONFIRM = 7; //确认收货

    @SerializedName("bt_flag")
    private int bt_flag;
    @SerializedName("bt_name")
    private String bt_name;

    public int getBt_flag() {
        return bt_flag;
    }

    public void setBt_flag(int bt_flag) {
        this.bt_flag = bt_flag;
    }

    public String getBt_name() {
        return bt_name;
    }

    public void setBt_name(String bt_name) {
        this.bt_name = bt_name;
    }
}
