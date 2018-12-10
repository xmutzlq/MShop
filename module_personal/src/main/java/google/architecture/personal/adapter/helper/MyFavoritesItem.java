package google.architecture.personal.adapter.helper;

import java.util.UUID;

import google.architecture.coremodel.data.FavoriteData;
import google.architecture.coremodel.data.MultiHeaderEntity;

/**
 * @author lq.zeng
 * @date 2018/10/24
 */

public class MyFavoritesItem implements MultiHeaderEntity {

    private String title;
    private String price;
    private String imgUrl;
    private long id;

    private boolean isChecked;

    public MyFavoritesItem(long id, String title, String price, String imgUrl) {
        this.title = title;
        this.price = price;
        this.imgUrl = imgUrl;
        this.id = id;
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
    public long getHeaderId() {
        return -1;
    }

    @Override
    public int getItemType() {
        return FavoriteData.FavoriteItem.TYPE_USER_MY_FAVORITE;
    }

    @Override
    public long getId() {
        if (id == 0) {
            return id = UUID.nameUUIDFromBytes((title).getBytes()).hashCode();
        }
        return id;
    }
}
