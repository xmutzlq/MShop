package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/8/28
 */

public class DetailMainInfo {

    @SerializedName("cart_num")
    private String cart_num;

    @SerializedName("item")
    private DetailCommInfo item;

    @SerializedName("goods_images_list")
    private List<String> goods_images_list;

    @SerializedName("spec_goods_list")
    private List<DetailSpecInfo> spec_goods_list;

    @SerializedName("spec_select_info")
    private DetailSpecSelectedInfo spec_select_info;

    @SerializedName("comment_list")
    private List<DetailCommentInfo> comment_list;

    @SerializedName("consult_list")
    private List<DetailConsultInfo> consult_list;

    @SerializedName("goods_recommend_list")
    private List<DetailRecommendInfo> goods_recommend_list;

    @SerializedName("goods_sale_list")
    private List<DetailRecommendInfo> goods_sale_list;

    public DetailCommInfo getItem() {
        return item;
    }

    public void setItem(DetailCommInfo item) {
        this.item = item;
    }

    public List<String> getGoods_images_list() {
        return goods_images_list;
    }

    public void setGoods_images_list(List<String> goods_images_list) {
        this.goods_images_list = goods_images_list;
    }

    public List<DetailSpecInfo> getSpec_goods_list() {
        return spec_goods_list;
    }

    public void setSpec_goods_list(List<DetailSpecInfo> spec_goods_list) {
        this.spec_goods_list = spec_goods_list;
    }

    public DetailSpecSelectedInfo getSpec_select_info() {
        return spec_select_info;
    }

    public void setSpec_select_info(DetailSpecSelectedInfo spec_select_info) {
        this.spec_select_info = spec_select_info;
    }

    public List<DetailCommentInfo> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<DetailCommentInfo> comment_list) {
        this.comment_list = comment_list;
    }

    public List<DetailConsultInfo> getConsult_list() {
        return consult_list;
    }

    public void setConsult_list(List<DetailConsultInfo> consult_list) {
        this.consult_list = consult_list;
    }

    public List<DetailRecommendInfo> getGoods_recommend_list() {
        return goods_recommend_list;
    }

    public void setGoods_recommend_list(List<DetailRecommendInfo> goods_recommend_list) {
        this.goods_recommend_list = goods_recommend_list;
    }

    public List<DetailRecommendInfo> getGoods_sale_list() {
        return goods_sale_list;
    }

    public void setGoods_sale_list(List<DetailRecommendInfo> goods_sale_list) {
        this.goods_sale_list = goods_sale_list;
    }

    public String getCart_num() {
        return cart_num;
    }

    public void setCart_num(String cart_num) {
        this.cart_num = cart_num;
    }
}
