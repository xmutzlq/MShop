package google.architecture.personal.adapter.helper;

import java.util.UUID;

import google.architecture.common.params.StickyItem;
import google.architecture.coremodel.data.FootprintInfo;

/**
 * @author lq.zeng
 * @date 2018/10/18
 */

public class FootprintItem implements StickyItem {

    private String title;
    private String price;
    private String imgUrl;
    private long id;
    private long headerId;

    private boolean isChecked;

    private String stickyName = "粘性";

    public FootprintItem(long id, String title, String price, String imgUrl, String stickyName) {
        this.title = title;
        this.price = price;
        this.imgUrl = imgUrl;
        this.id = id;
        this.stickyName = stickyName;
        headerId = UUID.nameUUIDFromBytes((stickyName).getBytes()).hashCode();
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imgUrl;
    }

    public String getPrice() {
        return price;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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
        return headerId;
    }

    @Override
    public int getItemType() {
        return FootprintInfo.TYPE_USER_FOOT_PRINT;
    }

    @Override
    public long getId() {
        if (id == 0) {
            return id = UUID.nameUUIDFromBytes((title + stickyName).getBytes()).hashCode();
        }
        return id;
    }

    @Override
    public String toString() {
        return "FootprintItem{" +
                "title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", id=" + id +
                '}';
    }
}
