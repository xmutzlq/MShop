package google.architecture.coremodel.data.xlj.shopdata;

import com.google.gson.annotations.SerializedName;

public class Goods {

    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("pdu")
    private String pdu;
    @SerializedName("goodsId")
    private long goodsId;
    @SerializedName("goodsName")
    private String goodsName;
    @SerializedName("shopPrice")
    private float shopPrice;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPdu() {
        return pdu;
    }

    public void setPdu(String pdu) {
        this.pdu = pdu;
    }

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

    public float getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(float shopPrice) {
        this.shopPrice = shopPrice;
    }
}
