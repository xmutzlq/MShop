package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/8/28
 */

public class DetailRankInfo {
    @SerializedName("goods_id")
    private String goods_id; //商品id
    @SerializedName("goods_name")
    private String goods_name; //商品名称
    @SerializedName("shop_price")
    private String shop_price; //价格
    @SerializedName("original_img")
    private String original_img; //图片

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getOriginal_img() {
        return original_img;
    }

    public void setOriginal_img(String original_img) {
        this.original_img = original_img;
    }
}
