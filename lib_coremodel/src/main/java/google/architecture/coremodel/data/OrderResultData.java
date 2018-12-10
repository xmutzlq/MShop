package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/9/11
 */

public class OrderResultData {
    @SerializedName("sum_order_money")
    private String sum_order_money; //支付金额
    @SerializedName("order_no")
    private String order_no; //订单号

    public String getSum_order_money() {
        return sum_order_money;
    }

    public void setSum_order_money(String sum_order_money) {
        this.sum_order_money = sum_order_money;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }
}
