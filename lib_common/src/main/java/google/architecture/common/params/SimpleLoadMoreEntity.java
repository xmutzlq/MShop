package google.architecture.common.params;

import java.util.UUID;

/**
 * @author lq.zeng
 * @date 2018/10/23
 */

public class SimpleLoadMoreEntity extends DefaultMultiHeaderEntity {

    public static final int TYPE_LOAD_MORE = 99;

    private long id;

    private int type;

    private boolean isShown;

    public SimpleLoadMoreEntity() {
        this.id = UUID.nameUUIDFromBytes(("load_more_" + type).getBytes()).hashCode();
        this.type = TYPE_LOAD_MORE;
    }

    public SimpleLoadMoreEntity(int type) {
        this.id = UUID.nameUUIDFromBytes(("load_more_" + type).getBytes()).hashCode();
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setShown(boolean isShown) {
        this.isShown = isShown;
    }

    @Override
    public int getItemType() {
        return type;
    }

    @Override
    public long getId() {
        return id;
    }

    public boolean isShown() {
        return isShown;
    }
}
