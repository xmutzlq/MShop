package google.architecture.personal.adapter.helper;

import java.util.UUID;

import google.architecture.common.params.StickyItem;

/**
 * @author lq.zeng
 * @date 2018/10/19
 */

public class FootprintHeaderItem implements StickyItem {

    private String title;
    private long id;

    private String stickyName = "粘性";

    public FootprintHeaderItem(long id, String title) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void setHeaderId(long headerId) {

    }

    @Override
    public void setStickyName(String stickyName) {

    }

    @Override
    public String getStickyName() {
        return stickyName;
    }

    @Override
    public long getHeaderId() {
        return 1;
    }

    @Override
    public int getItemType() {
        return FootprintAdapterHelper.LEVEL_FOOT_PRINT - 1000;
    }

    @Override
    public long getId() {
        if (id == 0) {
            return id = UUID.nameUUIDFromBytes((title + stickyName).getBytes()).hashCode();
        }
        return id;
    }
}
