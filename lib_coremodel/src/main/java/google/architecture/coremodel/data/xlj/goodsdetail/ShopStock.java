package google.architecture.coremodel.data.xlj.goodsdetail;

import com.google.gson.annotations.SerializedName;

public class ShopStock {
    @SerializedName("allStock")
    private int allStock;
    @SerializedName("unStock")
    private int unStock;

    public int getAllStock() {
        return allStock;
    }

    public void setAllStock(int allStock) {
        this.allStock = allStock;
    }

    public int getUnStock() {
        return unStock;
    }

    public void setUnStock(int unStock) {
        this.unStock = unStock;
    }
}
