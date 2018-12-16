package google.architecture.coremodel.data.xlj.goodsdetail;

import com.google.gson.annotations.SerializedName;

public class Json {
    @SerializedName("id")
    private int id;
    @SerializedName("allsaleId")
    private int allsaleId;
    @SerializedName("orderMoney")
    private int orderMoney;
    @SerializedName("favourableJson")
    private FavourableJson favourableJson;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setAllsaleId(int allsaleId) {
        this.allsaleId = allsaleId;
    }
    public int getAllsaleId() {
        return allsaleId;
    }

    public void setOrderMoney(int orderMoney) {
        this.orderMoney = orderMoney;
    }
    public int getOrderMoney() {
        return orderMoney;
    }

    public void setFavourableJson(FavourableJson favourableJson) {
        this.favourableJson = favourableJson;
    }
    public FavourableJson getFavourableJson() {
        return favourableJson;
    }

}
