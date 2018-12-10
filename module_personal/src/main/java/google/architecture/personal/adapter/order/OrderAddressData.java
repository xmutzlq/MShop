package google.architecture.personal.adapter.order;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import google.architecture.coremodel.MultiItemTypeHelper;

/**
 * @author lq.zeng
 * @date 2018/9/28
 */

public class OrderAddressData implements MultiItemEntity {

    private String consignee; //收货人
    private String mobile; //电话
    private String address; //配送地址

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

    @Override
    public int getItemType() {
        return MultiItemTypeHelper.TYPE_ORDER_ADDRESS;
    }
}
