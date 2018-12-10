package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/9/7
 */

public class OrderData {
    @SerializedName("all_goods_price")
    private String all_goods_price;

    @SerializedName("all_goods_num")
    private int all_goods_num;

    @SerializedName("order_list")
    private List<OrderRemoteShopData> order_list;

    @SerializedName("address_item")
    private OrderAddress address_item; //默认的配送地址

    public String getAll_goods_price() {
        return all_goods_price;
    }

    public void setAll_goods_price(String all_goods_price) {
        this.all_goods_price = all_goods_price;
    }

    public int getAll_goods_num() {
        return all_goods_num;
    }

    public void setAll_goods_num(int all_goods_num) {
        this.all_goods_num = all_goods_num;
    }

    public List<OrderRemoteShopData> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<OrderRemoteShopData> order_list) {
        this.order_list = order_list;
    }

    public OrderAddress getAddress_item() {
        return address_item;
    }

    public void setAddress_item(OrderAddress address_item) {
        this.address_item = address_item;
    }
}
