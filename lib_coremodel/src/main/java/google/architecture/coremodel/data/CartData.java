package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/8/30
 */

public class CartData {

    @SerializedName("all_goods_price")
    private String all_goods_price;

    @SerializedName("all_goods_num")
    private int all_goods_num; //所有店铺商品购买总数

    @SerializedName("all_goods_count")
    private int all_goods_count; //所有店铺商品的数量（不含每件购买数量）

    @SerializedName("select_goods_price")
    private String select_goods_price;

    @SerializedName("select_goods_num")
    private int select_goods_num; //选中的所有店铺商品购买总数

    @SerializedName("select_goods_count")
    private int select_goods_count; //选中的商品的数量（不含每件购买数量）

    @SerializedName("cart_list")
    private List<CartRemoteBusinessesData> cart_list;

    @SerializedName("goods_recommend_list")
    private List<DetailRecommendInfo> goods_recommend_list;

    public List<CartRemoteBusinessesData> getCart_list() {
        return cart_list;
    }

    public void setCart_list(List<CartRemoteBusinessesData> cart_list) {
        this.cart_list = cart_list;
    }

    public List<DetailRecommendInfo> getGoods_recommend_list() {
        return goods_recommend_list;
    }

    public void setGoods_recommend_list(List<DetailRecommendInfo> goods_recommend_list) {
        this.goods_recommend_list = goods_recommend_list;
    }

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

    public int getAll_goods_count() {
        return all_goods_count;
    }

    public void setAll_goods_count(int all_goods_count) {
        this.all_goods_count = all_goods_count;
    }

    public String getSelect_goods_price() {
        return select_goods_price;
    }

    public void setSelect_goods_price(String select_goods_price) {
        this.select_goods_price = select_goods_price;
    }

    public int getSelect_goods_num() {
        return select_goods_num;
    }

    public void setSelect_goods_num(int select_goods_num) {
        this.select_goods_num = select_goods_num;
    }

    public int getSelect_goods_count() {
        return select_goods_count;
    }

    public void setSelect_goods_count(int select_goods_count) {
        this.select_goods_count = select_goods_count;
    }
}
