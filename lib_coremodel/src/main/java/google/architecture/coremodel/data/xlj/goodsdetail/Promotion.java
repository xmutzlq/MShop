package google.architecture.coremodel.data.xlj.goodsdetail;

import com.google.gson.annotations.SerializedName;

public class Promotion {
    @SerializedName("order")
    private Order order;
    @SerializedName("good")
    private Good good;

    public void setOrder(Order order) {
        this.order = order;
    }
    public Order getOrder() {
        return order;
    }

    public void setGood(Good good) {
        this.good = good;
    }
    public Good getGood() {
        return good;
    }

}
