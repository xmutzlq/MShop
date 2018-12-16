package google.architecture.coremodel.data.xlj.goodsdetail;

import com.google.gson.annotations.SerializedName;

public class List {
    @SerializedName("isAllowImg")
    private int isAllowImg;
    @SerializedName("catName")
    private String catName;
    @SerializedName("productNo")
    private String productNo;
    @SerializedName("catId")
    private int catId;
    @SerializedName("itemId")
    private int itemId;
    @SerializedName("itemName")
    private String itemName;
    @SerializedName("itemImg")
    private String itemImg;
    @SerializedName("isDefault")
    private int isDefault;
    @SerializedName("specStock")
    private int specStock;
    @SerializedName("shopStock")
    private String shopStock;

    public void setIsAllowImg(int isAllowImg) {
        this.isAllowImg = isAllowImg;
    }
    public int getIsAllowImg() {
        return isAllowImg;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
    public String getCatName() {
        return catName;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
    public String getProductNo() {
        return productNo;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }
    public int getCatId() {
        return catId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public int getItemId() {
        return itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }
    public String getItemImg() {
        return itemImg;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }
    public int getIsDefault() {
        return isDefault;
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
