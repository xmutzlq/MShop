package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/8/30
 */

public class CartRemoteGoodsData {
    @SerializedName("id")
    private String id; //购物车id
    @SerializedName("goods_id")
    private String goods_id; //商品id
    @SerializedName("goods_name")
    private String goods_name; //商品名称
    @SerializedName("image")
    private String image; //图片
    @SerializedName("goods_price")
    private String goods_price; //价格
    @SerializedName("goods_num")
    private int goods_num; //购买数量
    @SerializedName("buy_limit")
    private int buy_limit; //单次限购
    @SerializedName("selected")
    private int selected; //购物车选中状态 1=选中 -1=否
    @SerializedName("store_count")
    private int store_count; //商品库存
    @SerializedName("spec_depict")
    private String spec_depict; //选中规格描述
    @SerializedName("change_depict")
    private String change_depict; //变动信息描述
    @SerializedName("state")
    private int state; //状态 1=正常可购买 -1=异常（要重选规格）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public int getBuy_limit() {
        return buy_limit;
    }

    public void setBuy_limit(int buy_limit) {
        this.buy_limit = buy_limit;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getStore_count() {
        return store_count;
    }

    public void setStore_count(int store_count) {
        this.store_count = store_count;
    }

    public String getSpec_depict() {
        return spec_depict;
    }

    public void setSpec_depict(String spec_depict) {
        this.spec_depict = spec_depict;
    }

    public String getChange_depict() {
        return change_depict;
    }

    public void setChange_depict(String change_depict) {
        this.change_depict = change_depict;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
