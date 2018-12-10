package google.architecture.personal.adapter.order;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import google.architecture.common.widget.adapter.MultipleItemRvAdapter;

/**
 * @author lq.zeng
 * @date 2018/9/13
 */

public class MyOrderAdapter extends MultipleItemRvAdapter<MyOrderSpc, BaseViewHolder> {

    public static final int TYPE_SHOP = 100; //商店
    public static final int TYPE_GOODS = 200; //货物
    public static final int TEXT_PAY = 300; //支付

    private PayProvider.IOperateOrder operateOrder;

    public MyOrderAdapter(@Nullable List<MyOrderSpc> data, PayProvider.IOperateOrder operateOrder) {
        super(data);
        this.operateOrder = operateOrder;
        finishInitialize();
    }

    @Override
    protected int getViewType(MyOrderSpc myOrderSpc) {
        if (myOrderSpc.type == MyOrderSpc.TYPE_SHOP) {
            return TYPE_SHOP;
        } else if (myOrderSpc.type == MyOrderSpc.TYPE_GOODS) {
            return TYPE_GOODS;
        } else if (myOrderSpc.type == MyOrderSpc.TEXT_PAY) {
            return TEXT_PAY;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new ShopProvider());
        mProviderDelegate.registerProvider(new GoodsProvider());
        mProviderDelegate.registerProvider(new PayProvider(operateOrder));
    }

    public void itemRangeMoved(int from, int to) {
        if(to <= from) return;
        for (int i = to; i >= from; i--) {
            remove(i);
        }
    }

    public int getPositionStart(int positionEnd) {
        return positionEnd - mProviderDelegate.getItemProviders().size() + 1;
    }
}
