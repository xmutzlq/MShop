package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/9/7
 */

public class OrderRemoteShopData {
    @SerializedName("supplier_id")
    private String supplier_id; //店铺id
    @SerializedName("shop_name")
    private String shop_name; //店铺名称
    @SerializedName("shipping_list")
    private List<OrderDispatchingList> shipping_list;
    @SerializedName("sum_goods_num")
    private int sum_goods_num; //当前店铺 总商品数
    @SerializedName("sum_goods_price")
    private String sum_goods_price; //当前店铺 商品总价格
    @SerializedName("shop_list")
    private List<OrderRemoteGoodsData> shop_list;

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

    public List<OrderDispatchingList> getShipping_list() {
        return shipping_list;
    }

    public void setShipping_list(List<OrderDispatchingList> shipping_list) {
        this.shipping_list = shipping_list;
    }

    public int getSum_goods_num() {
        return sum_goods_num;
    }

    public void setSum_goods_num(int sum_goods_num) {
        this.sum_goods_num = sum_goods_num;
    }

    public String getSum_goods_price() {
        return sum_goods_price;
    }

    public void setSum_goods_price(String sum_goods_price) {
        this.sum_goods_price = sum_goods_price;
    }

    public List<OrderRemoteGoodsData> getShop_list() {
        return shop_list;
    }

    public void setShop_list(List<OrderRemoteGoodsData> shop_list) {
        this.shop_list = shop_list;
    }
}
