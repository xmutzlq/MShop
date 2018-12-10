package google.architecture.coremodel.data;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author lq.zeng
 * @date 2018/9/6
 */

public abstract class CartExpandableItem extends AbstractExpandableItem<CartGoodsData> implements MultiItemEntity {
    public boolean isCehcked;
    public boolean isEnable;
    public boolean isDeleted;
}
