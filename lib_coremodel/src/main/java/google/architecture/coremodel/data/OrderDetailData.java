package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/9/28
 */

public class OrderDetailData {

    @SerializedName("order_id")
    private String order_id; //订单ID

    @SerializedName("consignee")
    private String consignee; //收货人
    @SerializedName("mobile")
    private String mobile; //电话
    @SerializedName("address")
    private String address; //配送地址

    @SerializedName("supplier_id")
    private String supplier_id; //店铺ID
    @SerializedName("shop_name")
    private String shop_name; //店铺名称

    @SerializedName("order_state")
    private int order_state; //订单状态： 1=待付款,2=待发货,3=待收货,4=待评价,5=订单完成,6=订单取消
    @SerializedName("state_desc")
    private String state_desc; //状态描述

    @SerializedName("shop_list")
    private List<OrderRemoteGoodsData> shop_list;

    @SerializedName("order_no")
    private String order_no; //订单号
    @SerializedName("create_time")
    private String create_time;  //下单时间
    @SerializedName("order_money")
    private String order_money;  //应付款金额
    @SerializedName("total_money")
    private String total_money;  //商品总金额
    @SerializedName("discount_money")
    private String discount_money;  //优惠金额
    @SerializedName("shipping_price")
    private String shipping_price;  //运费金额
    @SerializedName("pay_way")
    private String pay_way;  //支付方式

    @SerializedName("cancel_reason_list")
    private List<String> cancel_reason_list; //订单取消的原因选择

    @SerializedName("button_list")
    private List<MyOrderOper> button_list;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getState_desc() {
        return state_desc;
    }

    public void setState_desc(String state_desc) {
        this.state_desc = state_desc;
    }

    public List<OrderRemoteGoodsData> getShop_list() {
        return shop_list;
    }

    public void setShop_list(List<OrderRemoteGoodsData> shop_list) {
        this.shop_list = shop_list;
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

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public List<String> getCancel_reason_list() {
        return cancel_reason_list;
    }

    public void setCancel_reason_list(List<String> cancel_reason_list) {
        this.cancel_reason_list = cancel_reason_list;
    }

    public List<MyOrderOper> getButton_list() {
        return button_list;
    }

    public void setButton_list(List<MyOrderOper> button_list) {
        this.button_list = button_list;
    }
}
