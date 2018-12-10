package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/10/18
 */

public class FootprintInfo {

    public static final int TYPE_USER_FOOT_PRINT = 11;
    @SerializedName("visit_id")
    private String visit_id;
    @SerializedName("goods_id")
    private String goods_id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("visittime")
    private String visittime; //1539056078,
    @SerializedName("cat_id")
    private String cat_id;
    @SerializedName("gid")
    private String gid;
    @SerializedName("goods_name")
    private String goods_name;
    @SerializedName("category_path")
    private String category_path; //"3,27,191",
    @SerializedName("original_img")
    private String original_img;
    @SerializedName("market_price")
    private String market_price;
    @SerializedName("shop_price")
    private String shop_price;
    @SerializedName("goods_remark")
    private String goods_remark;
    @SerializedName("is_on_sale")
    private int is_on_sale;
    @SerializedName("item_ids")
    private String item_ids;
    @SerializedName("date")
    private String date;

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVisittime() {
        return visittime;
    }

    public void setVisittime(String visittime) {
        this.visittime = visittime;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
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

    public String getOriginal_img() {
        return original_img;
    }

    public void setOriginal_img(String original_img) {
        this.original_img = original_img;
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

    public int getIs_on_sale() {
        return is_on_sale;
    }

    public void setIs_on_sale(int is_on_sale) {
        this.is_on_sale = is_on_sale;
    }

    public String getItem_ids() {
        return item_ids;
    }

    public void setItem_ids(String item_ids) {
        this.item_ids = item_ids;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
