package google.architecture.personal.adapter.order;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.params.OrderGoodsData;
import google.architecture.common.params.OrderShopData;
import google.architecture.coremodel.data.OrderDetailData;
import google.architecture.coremodel.data.OrderRemoteGoodsData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lq.zeng
 * @date 2018/9/28
 */

public class OrderDetailDataResultHelper {
    private List<MultiItemEntity> multiItemEntityList;

    public OrderDetailDataResultHelper() {
        multiItemEntityList = new ArrayList<>();
    }

    public List<MultiItemEntity> getMultiItemEntityList() {
        return multiItemEntityList;
    }

    public void setMultiItemEntityList(List<MultiItemEntity> multiItemEntityList) {
        this.multiItemEntityList = multiItemEntityList;
    }

    public void getOrderAdapterData(OrderDetailData orderDetailData, IOrderDetailDataResult orderDataResult) {
        io.reactivex.Observable.just(orderDetailData)
                .flatMap(orderRemoteShopData -> io.reactivex.Observable.just(prepareAdapterData(orderRemoteShopData)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(multiItemEntities1 -> {
            if(orderDataResult != null) {
                orderDataResult.onOrderDetailDataResult(multiItemEntities1);
            }
        });
    }

    private List<MultiItemEntity> prepareAdapterData(OrderDetailData orderDetailData) {
        List<MultiItemEntity> multiItemEntities = new ArrayList<>();
        //订单状态
        OrderStateData orderStateData = new OrderStateData();
        orderStateData.setOrder_state(orderDetailData.getOrder_state());
        orderStateData.setState_desc(orderDetailData.getState_desc());
        multiItemEntities.add(orderStateData);
        //收货地址
        OrderAddressData orderAddressData = new OrderAddressData();
        orderAddressData.setConsignee(orderDetailData.getConsignee());
        orderAddressData.setMobile(orderDetailData.getMobile());
        orderAddressData.setAddress(orderDetailData.getAddress());
        multiItemEntities.add(orderAddressData);
        //店铺
        OrderShopData orderShopData = new OrderShopData();
        orderShopData.setSupplier_id(orderDetailData.getSupplier_id());
        orderShopData.setShop_name(orderDetailData.getShop_name());
        multiItemEntities.add(orderShopData);
        //物品
        for (OrderRemoteGoodsData orderRemoteGoodsData : orderDetailData.getShop_list()) {
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
        //支付信息
        OrderPayData orderPayData = new OrderPayData();
        orderPayData.setOrder_no(orderDetailData.getOrder_no());
        orderPayData.setCreate_time(orderDetailData.getCreate_time());
        orderPayData.setDiscount_money(orderDetailData.getDiscount_money());
        orderPayData.setShipping_price(orderDetailData.getShipping_price());
        orderPayData.setTotal_money(orderDetailData.getTotal_money());
        orderPayData.setOrder_money(orderDetailData.getOrder_money());
        multiItemEntities.add(orderPayData);
        return multiItemEntities;
    }

    public interface IOrderDetailDataResult {
        void onOrderDetailDataResult(List<MultiItemEntity> multiItemEntities);
    }

}
