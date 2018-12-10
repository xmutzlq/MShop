package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/9/19
 */

public class FavoriteData {
    @SerializedName("list")
    private List<FavoriteItem> list;
    @SerializedName("totalpage")
    private int totalpage;

    public List<FavoriteItem> getList() {
        return list;
    }

    public void setList(List<FavoriteItem> list) {
        this.list = list;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public static class FavoriteItem {
        public static final int TYPE_USER_MY_FAVORITE = 12;

        @SerializedName("goods_id")
        private String goodsId;
        @SerializedName("goods_name")
        private String goodsName;
        @SerializedName("category_path")
        private String categoryPath;
        @SerializedName("original_img")
        private String originalImg;
        @SerializedName("market_price")
        private String marketPrice;
        @SerializedName("shop_price")
        private String shopPrice;
        @SerializedName("goods_remark")
        private String goodsRemark;
        @SerializedName("is_on_sale")
        private int isOnSale;
        @SerializedName("item_ids")
        private String itemIds;

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getCategoryPath() {
            return categoryPath;
        }

        public void setCategoryPath(String categoryPath) {
            this.categoryPath = categoryPath;
        }

        public String getOriginalImg() {
            return originalImg;
        }

        public void setOriginalImg(String originalImg) {
            this.originalImg = originalImg;
        }

        public String getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(String marketPrice) {
            this.marketPrice = marketPrice;
        }

        public String getShopPrice() {
            return shopPrice;
        }

        public void setShopPrice(String shopPrice) {
            this.shopPrice = shopPrice;
        }

        public String getGoodsRemark() {
            return goodsRemark;
        }

        public void setGoodsRemark(String goodsRemark) {
            this.goodsRemark = goodsRemark;
        }

        public int getIsOnSale() {
            return isOnSale;
        }

        public void setIsOnSale(int isOnSale) {
            this.isOnSale = isOnSale;
        }

        public String getItemIds() {
            return itemIds;
        }

        public void setItemIds(String itemIds) {
            this.itemIds = itemIds;
        }
    }
}
