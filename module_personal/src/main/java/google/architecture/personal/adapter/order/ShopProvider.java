package google.architecture.personal.adapter.order;

import com.chad.library.adapter.base.BaseViewHolder;

import google.architecture.common.widget.adapter.BaseItemProvider;
import google.architecture.personal.R;

/**
 * @author lq.zeng
 * @date 2018/9/13
 */

public class ShopProvider extends BaseItemProvider<MyOrderSpc, BaseViewHolder> {

    @Override
    public int viewType() {
        return MyOrderAdapter.TYPE_SHOP;
    }

    @Override
    public int layout() {
        return R.layout.layout_my_order_shop_provider;
    }

    @Override
    public void convert(BaseViewHolder helper, MyOrderSpc data, int position) {
        helper.setText(R.id.my_order_create_time, data.createTime);
        if(data.myOrderShop != null) {
            helper.setText(R.id.my_order_pay_state, data.myOrderShop.getState_desc());
        }
    }

    @Override
    public void onClick(BaseViewHolder helper, MyOrderSpc data, int position) {
    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, MyOrderSpc data, int position) {
        return true;
    }
}
