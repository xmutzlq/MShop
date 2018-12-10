package com.king.android.order;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.params.OrderGoodsData;
import google.architecture.common.params.OrderShopData;
import google.architecture.coremodel.data.OrderRemoteGoodsData;
import google.architecture.coremodel.data.OrderRemoteShopData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lq.zeng
 * @date 2018/9/7
 */

public class OrderDataResultHelper {

    private List<MultiItemEntity> multiItemEntityList;

    public OrderDataResultHelper() {
        multiItemEntityList = new ArrayList<>();
    }

    public List<MultiItemEntity> getMultiItemEntityList() {
        return multiItemEntityList;
    }

    public void setMultiItemEntityList(List<MultiItemEntity> multiItemEntityList) {
        this.multiItemEntityList = multiItemEntityList;
    }

    public void getOrderAdapterData(List<OrderRemoteShopData> shopData, IOrderDataResult orderDataResult) {
        io.reactivex.Observable.just(shopData)
                .flatMap(orderRemoteShopData -> io.reactivex.Observable.just(prepareAdapterData(orderRemoteShopData)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(multiItemEntities1 -> {
            if(orderDataResult != null) {
                orderDataResult.onOrderDataResult(multiItemEntities1);
            }
        });
    }

    private List<MultiItemEntity> prepareAdapterData(List<OrderRemoteShopData> shopData) {
        List<MultiItemEntity> multiItemEntities = new ArrayList<>();
        for (OrderRemoteShopData orderRemoteShopData : shopData) {
            OrderShopData orderShopData = new OrderShopData();
            orderShopData.setSupplier_id(orderRemoteShopData.getSupplier_id());
            orderShopData.setShop_name(orderRemoteShopData.getShop_name());
            multiItemEntities.add(orderShopData);
            for (OrderRemoteGoodsData orderRemoteGoodsData : orderRemoteShopData.getShop_list()) {
                OrderGoodsData orderGoodsData = new OrderGoodsData();
                orderGoodsData.setGoods_id(orderRemoteGoodsData.getGoods_id());
                orderGoodsData.setGoods_name(orderRemoteGoodsData.getGoods_name());
                orderGoodsData.setGoods_num(orderRemoteGoodsData.getGoods_num());
                orderGoodsData.setGoods_price(orderRemoteGoodsData.getGoods_price());
                orderGoodsData.setImage(orderRemoteGoodsData.getImage());
                orderGoodsData.setSpec_depict(orderRemoteGoodsData.getSpec_depict());
                orderGoodsData.setItem_ids(orderRemoteGoodsData.getItem_ids());
                multiItemEntities.add(orderGoodsData);
            }
            OrderLeftData orderLeftData = new OrderLeftData();
            orderLeftData.supplierId = orderRemoteShopData.getSupplier_id();
            orderLeftData.setSum_goods_num(orderRemoteShopData.getSum_goods_num());
            orderLeftData.setSum_goods_price(orderRemoteShopData.getSum_goods_price());
            orderLeftData.setShipping_list(orderRemoteShopData.getShipping_list());
            multiItemEntities.add(orderLeftData);
        }
        return multiItemEntities;
    }

    /**留言对象**/
    public static class LeftParams {
        public String supplier_id;
        public String user_note;
    }

    public static class OrderParams {
        public String addressId;
        public String noteJson;
    }

    public interface IOrderDataResult {
        void onOrderDataResult(List<MultiItemEntity> multiItemEntities);
    }
}
