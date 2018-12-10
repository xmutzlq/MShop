package google.architecture.coremodel.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author lq.zeng
 * @date 2018/9/6
 */

public abstract class CartMultiItemEntity implements MultiItemEntity{
    public int count = 1;
    public boolean isChecked;
    public boolean isDeleteState;
}
