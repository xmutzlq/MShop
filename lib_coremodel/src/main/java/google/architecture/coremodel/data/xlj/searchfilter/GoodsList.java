package google.architecture.coremodel.data.xlj.searchfilter;

import com.google.gson.annotations.SerializedName;

public class GoodsList {
    @SerializedName("id")
    private String id;
    @SerializedName("goodsimg")
    private String goodsimg;
    @SerializedName("goodsname")
    private String goodsname;
    @SerializedName("shopprice")
    private String shopprice;
    @SerializedName("marketprice")
    private String marketprice;

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setGoodsimg(String goodsimg) {
        this.goodsimg = goodsimg;
    }
    public String getGoodsimg() {
        return goodsimg;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }
    public String getGoodsname() {
        return goodsname;
    }

    public void setShopprice(String shopprice) {
        this.shopprice = shopprice;
    }
    public String getShopprice() {
        return shopprice;
    }

    public void setMarketprice(String marketprice) {
        this.marketprice = marketprice;
    }
    public String getMarketprice() {
        return marketprice;
    }
}
