package com.king.android.order;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import google.architecture.coremodel.MultiItemTypeHelper;
import google.architecture.coremodel.data.OrderDispatchingList;

/**
 * @author lq.zeng
 * @date 2018/9/7
 */

public class OrderLeftData implements MultiItemEntity {

    @SerializedName("shipping_list")
    private List<OrderDispatchingList> shipping_list;
    @SerializedName("sum_goods_num")
    private int sum_goods_num; //当前店铺 总商品数
    @SerializedName("sum_goods_price")
    private String sum_goods_price; //当前店铺 商品总价格

    public String supplierId; //店铺Id
    public String leftStr; //留言

    public List<OrderDispatchingList> getShipping_list() {
        return shipping_list;
    }

    public void setShipping_list(List<OrderDispatchingList> shipping_list) {
        this.shipping_list = shipping_list;
    }

    public int getSum_goods_num() {
        return sum_goods_num;
    }

    public void setSum_goods_num(int sum_goods_num) {
        this.sum_goods_num = sum_goods_num;
    }

    public String getSum_goods_price() {
        return sum_goods_price;
    }

    public void setSum_goods_price(String sum_goods_price) {
        this.sum_goods_price = sum_goods_price;
    }

    @Override
    public int getItemType() {
        return MultiItemTypeHelper.TYPE_ORDER_LEFT;
    }

}
