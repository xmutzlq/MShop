package google.architecture.coremodel.data.xlj.searchfilter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoodsList {
    @SerializedName("id")
    private String id;
    @SerializedName("goodsimg")
    private String goodsimg;
    @SerializedName("goodsname")
    private String goodsname;
    @SerializedName("shopprice")
    private String shopprice;
    @SerializedName("saleprice")
    private String saleprice;
    @SerializedName("isgoodssale")
    private int isgoodssale;
    @SerializedName("marketprice")
    private String marketprice;
    @SerializedName("tagid")
    private List<Integer> tagid;
    @SerializedName("sales")
    private List<Sales> sales;

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

    public String getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice;
    }

    public int getIsgoodssale() {
        return isgoodssale;
    }

    public void setIsgoodssale(int isgoodssale) {
        this.isgoodssale = isgoodssale;
    }

    public void setMarketprice(String marketprice) {
        this.marketprice = marketprice;
    }
    public String getMarketprice() {
        return marketprice;
    }

    public List<Integer> getTagid() {
        return tagid;
    }

    public void setTagid(List<Integer> tagid) {
        this.tagid = tagid;
    }

    public List<Sales> getSales() {
        return sales;
    }

    public void setSales(List<Sales> sales) {
        this.sales = sales;
    }
}
