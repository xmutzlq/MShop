package google.architecture.coremodel.data.xlj.goodsdetail;

import com.google.gson.annotations.SerializedName;

public class SaleSpecChild {
    @SerializedName("id")
    private int id;
    @SerializedName("isDefault")
    private int isDefault;
    @SerializedName("productNo")
    private String productNo;
    @SerializedName("specIds")
    private String specIds;
    @SerializedName("marketPrice")
    private String marketPrice;
    @SerializedName("specPrice")
    private String specPrice;
    @SerializedName("specStock")
    private int specStock;
    @SerializedName("shopStock")
    private String shopStock;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }
    public int getIsDefault() {
        return isDefault;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
    public String getProductNo() {
        return productNo;
    }

    public void setSpecIds(String specIds) {
        this.specIds = specIds;
    }
    public String getSpecIds() {
        return specIds;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }
    public String getMarketPrice() {
        return marketPrice;
    }

    public void setSpecPrice(String specPrice) {
        this.specPrice = specPrice;
    }
    public String getSpecPrice() {
        return specPrice;
    }

    public void setSpecStock(int specStock) {
        this.specStock = specStock;
    }
    public int getSpecStock() {
        return specStock;
    }

    public void setShopStock(String shopStock) {
        this.shopStock = shopStock;
    }
    public String getShopStock() {
        return shopStock;
    }
}
