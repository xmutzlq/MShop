package google.architecture.common.params;

import google.architecture.coremodel.data.MultiHeaderEntity;

/**
 * @author lq.zeng
 * @date 2018/10/19
 */

public interface StickyItem extends MultiHeaderEntity {
    void setHeaderId(long headerId);

    void setStickyName(String stickyName);

    String getStickyName();
}
