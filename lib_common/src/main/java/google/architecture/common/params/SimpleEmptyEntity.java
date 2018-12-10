package google.architecture.common.params;

import java.util.UUID;

/**
 * @author lq.zeng
 * @date 2018/10/22
 */

public class SimpleEmptyEntity extends DefaultMultiHeaderEntity {

    private long id;

    private int type;

    public SimpleEmptyEntity(int type) {
        this.id = UUID.nameUUIDFromBytes(("empty_" + type).getBytes()).hashCode();
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(long id) {
        this.id = id;
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
