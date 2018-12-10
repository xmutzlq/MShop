package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/9/7
 */

public class OrderDispatchingList {
    @SerializedName("goods_id")
    private String shipping_id; //物流id
    @SerializedName("shiping_name")
    private String shiping_name; //物流描述
    @SerializedName("shiping_price")
    private String shiping_price;

    public String getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(String shipping_id) {
        this.shipping_id = shipping_id;
    }

    public String getShiping_name() {
        return shiping_name;
    }

    public void setShiping_name(String shiping_name) {
        this.shiping_name = shiping_name;
    }

    public String getShiping_price() {
        return shiping_price;
    }

    public void setShiping_price(String shiping_price) {
        this.shiping_price = shiping_price;
    }
}
