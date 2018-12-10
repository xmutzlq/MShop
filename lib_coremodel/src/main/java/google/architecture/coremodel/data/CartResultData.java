package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/9/3
 */

public class CartResultData {
    @SerializedName("all_goods_price")
    private String all_goods_price; //所有店铺商品购买总价格
    @SerializedName("all_goods_num")
    private String all_goods_num; //所有店铺商品购买总数

    public String getAll_goods_price() {
        return all_goods_price;
    }

    public void setAll_goods_price(String all_goods_price) {
        this.all_goods_price = all_goods_price;
    }

    public String getAll_goods_num() {
        return all_goods_num;
    }

    public void setAll_goods_num(String all_goods_num) {
        this.all_goods_num = all_goods_num;
    }
}
