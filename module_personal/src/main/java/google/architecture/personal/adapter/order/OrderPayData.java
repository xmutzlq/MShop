package google.architecture.personal.adapter.order;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import google.architecture.coremodel.MultiItemTypeHelper;

/**
 * @author lq.zeng
 * @date 2018/9/28
 */

public class OrderPayData implements MultiItemEntity {

    private String order_no; //订单号
    private String create_time;  //下单时间
    private String order_money;  //应付款金额
    private String total_money;  //商品总金额
    private String discount_money;  //优惠金额
    private String shipping_price;  //运费金额

    @Override
    public int getItemType() {
        return MultiItemTypeHelper.TYPE_ORDER_PAY;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getOrder_money() {
        return order_money;
    }

    public void setOrder_money(String order_money) {
        this.order_money = order_money;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public String getDiscount_money() {
        return discount_money;
    }

    public void setDiscount_money(String discount_money) {
        this.discount_money = discount_money;
    }

    public String getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(String shipping_price) {
        this.shipping_price = shipping_price;
    }
}
