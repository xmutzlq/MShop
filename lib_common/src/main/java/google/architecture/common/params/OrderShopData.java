package google.architecture.common.params;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import google.architecture.coremodel.MultiItemTypeHelper;

/**
 * @author lq.zeng
 * @date 2018/9/7
 */

public class OrderShopData extends AbstractExpandableItem<OrderGoodsData> implements MultiItemEntity {
    private String supplier_id; //店铺id
    private String shop_name; //店铺名称

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return MultiItemTypeHelper.TYPE_ORDER_BUSINESSES;
    }
}
