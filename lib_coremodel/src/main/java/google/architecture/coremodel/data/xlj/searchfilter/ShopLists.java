package google.architecture.coremodel.data.xlj.searchfilter;

import com.google.gson.annotations.SerializedName;

public class ShopLists {
    @SerializedName("shopId")
    private int shopId;
    @SerializedName("shopName")
    private String shopName;

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
    public int getShopId() {
        return shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getShopName() {
        return shopName;
    }
}
