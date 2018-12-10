package google.architecture.personal.adapter.order;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import google.architecture.coremodel.MultiItemTypeHelper;

/**
 * @author lq.zeng
 * @date 2018/10/15
 */

public class OrderStateData implements MultiItemEntity {

    private int order_state;
    private String state_desc;

    @Override
    public int getItemType() {
        return MultiItemTypeHelper.TYPE_ORDER_STATE;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getState_desc() {
        return state_desc;
    }

    public void setState_desc(String state_desc) {
        this.state_desc = state_desc;
    }
}
