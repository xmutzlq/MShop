package google.architecture.personal.adapter.order;

import java.util.ArrayList;
import java.util.List;

import google.architecture.coremodel.data.MyOrderData;
import google.architecture.coremodel.data.MyOrderGoods;
import google.architecture.coremodel.data.MyOrderShop;

/**
 * @author lq.zeng
 * @date 2018/9/14
 */

public class MyOrderDataHelper {

    public static List<MyOrderSpc> prePareMyOrderListData(MyOrderData myOrderData) {
        if(myOrderData == null || myOrderData.getOrder_list() == null
                || myOrderData.getOrder_list().size() == 0) return null;
        List<MyOrderSpc> specDatas = new ArrayList<>();
        for (MyOrderShop shopInfo : myOrderData.getOrder_list()) {
            specDatas.add(buildShop(shopInfo));
            for (MyOrderGoods goodsInfo : shopInfo.getShop_list()) {
                specDatas.add(buildGoods(shopInfo, goodsInfo));
            }
            shopInfo.setCancel_reason_list(myOrderData.getCancel_reason_list());
            specDatas.add(buildPay(shopInfo));
        }
        return specDatas;
    }

    private static MyOrderSpc buildShop(MyOrderShop shopInfo) {
        MyOrderSpc myOrderSpc = new MyOrderSpc();
        myOrderSpc.orderId = shopInfo.getOrder_id();
        myOrderSpc.orderNo = shopInfo.getOrder_no();
        myOrderSpc.shopId = shopInfo.getSupplier_id();
        myOrderSpc.name = shopInfo.getShop_name();
        myOrderSpc.type = MyOrderSpc.TYPE_SHOP;
        myOrderSpc.createTime = shopInfo.getCreate_time();
        myOrderSpc.myOrderShop = shopInfo;
        return myOrderSpc;
    }

    private static MyOrderSpc buildGoods(MyOrderShop shopInfo, MyOrderGoods goodsInfo) {
        MyOrderSpc myOrderSpc = new MyOrderSpc();
        myOrderSpc.orderId = shopInfo.getOrder_id();
        myOrderSpc.orderNo = shopInfo.getOrder_no();
        myOrderSpc.goodsId = goodsInfo.getGoods_id();
        myOrderSpc.name = goodsInfo.getGoods_name();
        myOrderSpc.type = MyOrderSpc.TYPE_GOODS;
        myOrderSpc.myOrderGood = goodsInfo;
        return myOrderSpc;
    }

    private static MyOrderSpc buildPay(MyOrderShop shopInfo) {
        MyOrderSpc myOrderSpc = new MyOrderSpc();
        myOrderSpc.orderId = shopInfo.getOrder_id();
        myOrderSpc.orderNo = shopInfo.getOrder_no();
        myOrderSpc.type = MyOrderSpc.TEXT_PAY;
        myOrderSpc.MyOrderOpers = shopInfo.getButton_list();
        myOrderSpc.cancelReasonList = shopInfo.getCancel_reason_list();
        return myOrderSpc;
    }
}
