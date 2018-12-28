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
    private String catId;
    @SerializedName("itemId")
    private String itemId;
    @SerializedName("itemName")
    private String itemName;
    @SerializedName("itemImg")
    private String itemImg;
    @SerializedName("isDefault")
    private int isDefault;
    @SerializedName("specStock")
    private int specStock;
    @SerializedName("shopStock")
    private int shopStock = -1;

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

    public void setCatId(String catId) {
        this.catId = catId;
    }
    public String getCatId() {
        return catId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public String getItemId() {
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

    public void setShopStock(int shopStock) {
        this.shopStock = shopStock;
    }
    public int getShopStock() {
        return shopStock;
    }
}
