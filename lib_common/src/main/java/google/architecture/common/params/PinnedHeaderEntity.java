package google.architecture.common.params;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author lq.zeng
 * @date 2018/11/5
 */

public class PinnedHeaderEntity <T> implements MultiItemEntity {

    private final int itemType;

    private T data;

    private String pinnedHeaderName;

    public PinnedHeaderEntity(T data, int itemType, String pinnedHeaderName) {
        this.data = data;
        this.itemType = itemType;
        this.pinnedHeaderName = pinnedHeaderName;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setPinnedHeaderName(String pinnedHeaderName) {
        this.pinnedHeaderName = pinnedHeaderName;
    }

    public T getData() {
        return data;
    }

    public String getPinnedHeaderName() {
        return pinnedHeaderName;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
