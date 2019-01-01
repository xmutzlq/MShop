package google.architecture.coremodel.data.xlj.personal;

import com.google.gson.annotations.SerializedName;

public class LikeGoods {

    @SerializedName("goodsId")
    private long goodsId;
    @SerializedName("goodsName")
    private String goodsName;
    @SerializedName("goodsImg")
    private String goodsImg;
    @SerializedName("saleNum")
    private int saleNum;
    @SerializedName("shopPrice")
    private String shopPrice;
    @SerializedName("marketPrice")
    private String marketPrice;
    @SerializedName("isNew")
    private int isNew;

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public String getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }
}
