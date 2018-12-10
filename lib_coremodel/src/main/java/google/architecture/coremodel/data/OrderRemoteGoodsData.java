package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/9/7
 */

public class OrderRemoteGoodsData {
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
    @SerializedName("spec_depict")
    private String spec_depict; //选中规格描述
    @SerializedName("item_ids")
    private String item_ids; //商品规格ids

    public static final int min_input_size = 8;
    public String commentInputValue = "";
    public String commentLevel = "1";

    public List<ImageFile> imgUploads;

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

    public String getSpec_depict() {
        return spec_depict;
    }

    public void setSpec_depict(String spec_depict) {
        this.spec_depict = spec_depict;
    }

    public String getItem_ids() {
        return item_ids;
    }

    public void setItem_ids(String item_ids) {
        this.item_ids = item_ids;
    }

}
