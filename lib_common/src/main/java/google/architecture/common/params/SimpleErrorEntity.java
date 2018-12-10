package google.architecture.common.params;

import java.util.UUID;

/**
 * @author lq.zeng
 * @date 2018/10/22
 */

public class SimpleErrorEntity extends DefaultMultiHeaderEntity {

    private long id;
    private int type;

    public SimpleErrorEntity(int type) {
        this.id = UUID.nameUUIDFromBytes(("error_" + type).getBytes()).hashCode();
        this.type = type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }

    @Override
    public long getId() {
        return id;
    }
}
