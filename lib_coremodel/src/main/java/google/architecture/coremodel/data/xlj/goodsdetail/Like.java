package google.architecture.coremodel.data.xlj.goodsdetail;

import com.google.gson.annotations.SerializedName;

public class Like {
    @SerializedName("goodsId")
    private String goodsId;
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

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }
    public String getGoodsImg() {
        return goodsImg;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }
    public int getSaleNum() {
        return saleNum;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }
    public String getShopPrice() {
        return shopPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }
    public String getMarketPrice() {
        return marketPrice;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }
    public int getIsNew() {
        return isNew;
    }

}
