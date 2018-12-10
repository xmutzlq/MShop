package google.architecture.common.params;

import google.architecture.coremodel.data.MultiHeaderEntity;

/**
 * @author lq.zeng
 * @date 2018/10/22
 */

public abstract class DefaultMultiHeaderEntity implements MultiHeaderEntity {

    @Override
    public long getHeaderId() {
        return -1;
    }
}
