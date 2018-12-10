package google.architecture.common.params;

import java.util.UUID;

/**
 * @author lq.zeng
 * @date 2018/10/22
 */

public class LoadingEntity extends DefaultMultiHeaderEntity {

    private long id;

    private int type;

    public LoadingEntity(int type) {
        this.id = UUID.nameUUIDFromBytes(("loading_" + type).getBytes()).hashCode();
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
