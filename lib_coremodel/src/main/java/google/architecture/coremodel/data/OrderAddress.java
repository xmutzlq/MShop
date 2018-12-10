package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/9/7
 */

public class OrderAddress {
    @SerializedName("address_id")
    private String address_id; //地址id
    @SerializedName("consignee")
    private String consignee; //收货人
    @SerializedName("mobile")
    private String mobile; //电话
    @SerializedName("address")
    private String address; //配送地址

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
