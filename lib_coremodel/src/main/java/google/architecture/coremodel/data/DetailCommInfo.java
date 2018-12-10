package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/8/28
 */

public class DetailCommInfo {
    @SerializedName("goods_id")
    private String goods_id; //商品id
    @SerializedName("goods_name")
    private String goods_name; //商品名称
    @SerializedName("category_path")
    private String category_path; //商品所属分类
    @SerializedName("market_price")
    private String market_price; //市场价
    @SerializedName("shop_price")
    private String shop_price; //当前售价
    @SerializedName("goods_remark")
    private String goods_remark; //商品简述
    @SerializedName("is_on_sale")
    private String is_on_sale;
    @SerializedName("goods_user_collect")
    private String goods_user_collect; //用户是否收藏商品 >0已收藏
    @SerializedName("comment_count")
    private String comment_count; //商品评论总数
    @SerializedName("consult_count")
    private String consult_count; //商品咨询总数
    @SerializedName("comment_score")
    private String comment_score; //好评分数

    private String detail_url;
    @SerializedName("chat_url")
    private String chat_url;

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

    public String getCategory_path() {
        return category_path;
    }

    public void setCategory_path(String category_path) {
        this.category_path = category_path;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getGoods_remark() {
        return goods_remark;
    }

    public void setGoods_remark(String goods_remark) {
        this.goods_remark = goods_remark;
    }

    public String getIs_on_sale() {
        return is_on_sale;
    }

    public void setIs_on_sale(String is_on_sale) {
        this.is_on_sale = is_on_sale;
    }

    public String getGoods_user_collect() {
        return goods_user_collect;
    }

    public void setGoods_user_collect(String goods_user_collect) {
        this.goods_user_collect = goods_user_collect;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getConsult_count() {
        return consult_count;
    }

    public void setConsult_count(String consult_count) {
        this.consult_count = consult_count;
    }

    public String getComment_score() {
        return comment_score;
    }

    public void setComment_score(String comment_score) {
        this.comment_score = comment_score;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    public String getChat_url() {
        return chat_url;
    }

    public void setChat_url(String chat_url) {
        this.chat_url = chat_url;
    }
}
