package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/9/13
 */

public class MyOrderShop {
    @SerializedName("order_id")
    private String order_id;
    @SerializedName("order_no")
    private String order_no;
    @SerializedName("supplier_id")
    private String supplier_id;
    @SerializedName("shop_name")
    private String shop_name;
    @SerializedName("goods_num")
    private String goods_num;
    @SerializedName("order_money")
    private String order_money;
    @SerializedName("create_time")
    private String create_time;
    @SerializedName("shipping_price")
    private String shipping_price;
    @SerializedName("state_desc")
    private String state_desc;
    @SerializedName("order_state")
    private String order_state;

    @SerializedName("button_list")
    private List<MyOrderOper> button_list;

    @SerializedName("shop_list")
    private List<MyOrderGoods> shop_list;

    @SerializedName("cancel_reason_list")
    private List<String> cancel_reason_list;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getOrder_money() {
        return order_money;
    }

    public void setOrder_money(String order_money) {
        this.order_money = order_money;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(String shipping_price) {
        this.shipping_price = shipping_price;
    }

    public String getState_desc() {
        return state_desc;
    }

    public void setState_desc(String state_desc) {
        this.state_desc = state_desc;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public List<MyOrderOper> getButton_list() {
        return button_list;
    }

    public void setButton_list(List<MyOrderOper> button_list) {
        this.button_list = button_list;
    }

    public List<MyOrderGoods> getShop_list() {
        return shop_list;
    }

    public void setShop_list(List<MyOrderGoods> shop_list) {
        this.shop_list = shop_list;
    }

    public List<String> getCancel_reason_list() {
        return cancel_reason_list;
    }

    public void setCancel_reason_list(List<String> cancel_reason_list) {
        this.cancel_reason_list = cancel_reason_list;
    }
}
