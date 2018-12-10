package google.architecture.personal.adapter.order;

import java.util.List;

import google.architecture.coremodel.data.MyOrderGoods;
import google.architecture.coremodel.data.MyOrderOper;
import google.architecture.coremodel.data.MyOrderShop;

/**
 * @author lq.zeng
 * @date 2018/9/13
 */

public class MyOrderSpc {
    public static final int TYPE_SHOP = 1; //店铺
    public static final int TYPE_GOODS = 2; //物品
    public static final int TEXT_PAY = 3; //支付

    public String orderId; //订单id
    public String orderNo; //订单编号
    public String shopId; //店铺id
    public String goodsId; //物品id
    public int type; //类型
    public String name; //名称
    public String createTime; //订单创建时间

    public MyOrderGoods myOrderGood; // 物品
    public MyOrderShop myOrderShop; // 店铺
    public List<MyOrderOper> MyOrderOpers; //functions功能(支付、取消等等)
    public List<String> cancelReasonList; //取消订单的原因
}
